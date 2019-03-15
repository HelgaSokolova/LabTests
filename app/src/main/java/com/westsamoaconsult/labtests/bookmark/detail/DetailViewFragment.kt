package com.westsamoaconsult.labtests.bookmark.detail

import android.graphics.Color
import android.icu.text.Normalizer.NO
import android.icu.text.Normalizer.YES
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.bookmark.second.SecondViewAdapter
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.database.ArticleItem
import com.westsamoaconsult.labtests.database.DateSerializer
import com.westsamoaconsult.labtests.utils.Constants
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.info_fragment.*
import kotlinx.android.synthetic.main.item_default.*
import kotlinx.serialization.context.SimpleModule
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.collections.ArrayList
import android.support.design.widget.BottomSheetDialog
import android.R




class DetailViewFragment: BaseFragment(), SecondViewAdapter.OnItemClickListener, View.OnClickListener {
    companion object {
        private val DateJson = Json(indented = true).apply { install(SimpleModule(Date::class, DateSerializer)) }

        fun newInstance(article: ArticleItem) = DetailViewFragment().apply {
            arguments = Bundle().apply {
                putString("articleItem", DateJson.stringify(ArticleItem.serializer(), article))
            }
        }
    }

    private lateinit var dataItem: ArticleItem
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private var data: MutableMap<String, Any>? = null;

    private val dotImages1 = arrayOf("Black.png", "Gold.png", "Gray.png", "Green.png", "Lavender", "Light Blue.png", "Light Green.png", "Orange.png", "Pink.png", "Red.png", "Royal Blue.png", "Tan.png", "White.png", "Yellow.png", "CSF.png", "Pico70.png");

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.westsamoaconsult.labtests.R.layout.bookmark_first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setBackgroundColor(Color.parseColor("#efeff4"))

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity!!);
        dataItem = DateJson.parse(ArticleItem.serializer(), arguments!!.getString("articleItem")!!)
    }

    override fun onClick(article: ArticleItem) {
    }

    override fun onForeground() {
        super.onForeground()

        (activity as BaseActivity).apply {
            setTitle(dataItem.name)
            setBackButtonVisible(true)
            if (dataItem.isFavorite) {
                setRightButtonVisible(true, com.westsamoaconsult.labtests.R.drawable.star_filled_22)
            } else {
                setRightButtonVisible(true, com.westsamoaconsult.labtests.R.drawable.star_nonfilled_22)
            }
        }

        loadHtmlFile()

        buildList()
    }

    override fun onRightButtonPressed() {
        if (dataItem.isFavorite) {
            dataItem.isFavorite = false
            (activity as BaseActivity).setRightButtonVisible(true, com.westsamoaconsult.labtests.R.drawable.star_nonfilled_22)
        }
        else {
            dataItem.isFavorite = true
            (activity as BaseActivity).setRightButtonVisible(true, com.westsamoaconsult.labtests.R.drawable.star_filled_22)
        }

        val bundle = Bundle().apply {
            putString("article_name", dataItem.name)
            putBoolean("is_favourite", dataItem.isFavorite)
        }
        mFirebaseAnalytics.logEvent("article_favourite", bundle)

        MainApplication.instance.database.saveFavorites()
    }

    private fun loadHtmlFile() {
        if (Utils.loadData<Boolean>(Constants.NotFirstRun) == null) {
            Utils.saveData(Constants.NotFirstRun, true)

            AlertDialog.Builder(activity!!)
                .setTitle("Reference Range")
                .setMessage("How would you like the Reference Range to be displayed?\\n\\n(It can be changed under 'More')")
                .setPositiveButton("SI") { dialog, _ ->
                    Utils.saveData(Constants.REFERENCE_RANGE, "SI"); loadHtmlFile(); dialog.dismiss()
                }
                .setNegativeButton("US") { dialog, _ ->
                    Utils.saveData(Constants.REFERENCE_RANGE, "US"); loadHtmlFile(); dialog.dismiss()
                }
                .show()
        }
        dataItem.lastOpened = Date(0)
        MainApplication.instance.database.saveLastUsedTimes()

        val bundle = Bundle().apply {
            putString("article_name", dataItem.name)
        }
        mFirebaseAnalytics.logEvent("open_article", bundle)

        if (MainApplication.instance.FBDatabase.usable) {
            data = MainApplication.instance.FBDatabase.articleWithNumericAddress(dataItem.address.toInt()) as MutableMap<String, Any>
        }

        if (data == null) {
            val db = Utils.loadJSONFromAsset(String.format("%s.json", dataItem.address))
            val typeOfHashMap = object : TypeToken<MutableMap<String, Any>>() {}.type
            data = Gson().fromJson<MutableMap<String, Any>>(db, typeOfHashMap)
        }
    }

    private fun buildList() {
        val itemList = mutableListOf<DetailViewItem>()

        data!!.apply {
            (get("Article") as ArrayList<*>).forEach {
                (it as Map<*, *>).apply {
                    itemList.add(SectionDetailItem(get("header") as String, itemList.size == 0))

                    val displayString: String
                    if (Utils.loadData<String>(Constants.REFERENCE_RANGE) != "US") {
                        displayString = (get("paragraph_SI") as ArrayList<*>)[0] as String
                    } else {
                        displayString = (get("paragraph_US") as ArrayList<*>)[0] as String
                    }

                    if (displayString.contains(".png")) {
                        itemList.add(ImageDetailItem(displayString))
                        return@forEach
                    }

                    if (itemList.size == 1 ) {
                        val dict = Utils.loadData<Map<String, String>>("customDots")

                        dict?.get(dataItem.address)?.let {
                            itemList.add(HeaderDetailItem(displayString, it, this@DetailViewFragment))
                        } ?: run {
                            val imageName = (data!!.get("dot_image") as ArrayList<*>)[0] as String
                            itemList.add(HeaderDetailItem(displayString, imageName))
                        }
                        return@forEach
                    }

                    itemList.add(TextDetailItem(displayString))
                }
            }

        }

        recyclerView.adapter = DetailViewAdapter(itemList)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            com.westsamoaconsult.labtests.R.id.btnImage -> {
                val dialog = BottomSheetDialog(activity!!)
//                dialog.setContentView=

                dialog.show()


//                NSMutableParagraphStyle *paragraphStyle = NSMutableParagraphStyle.new;
//                paragraphStyle.lineBreakMode = NSLineBreakByWordWrapping;
//                paragraphStyle.alignment = NSTextAlignmentCenter;
//
//                NSAttributedString *title = [[NSAttributedString alloc] initWithString:@"C H A N G E   T H E   C O L O R" attributes:@{NSFontAttributeName : [UIFont boldSystemFontOfSize:14], NSParagraphStyleAttributeName : paragraphStyle}];
//
//                CNPPopupButton *button = [[CNPPopupButton alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width*0.85, 45)];
//                [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                button.titleLabel.font = [UIFont boldSystemFontOfSize:14];
//                [button setTitle:@"Cancel" forState:UIControlStateNormal];
//                button.layer.cornerRadius = 4.0f;
//                button.backgroundColor = [DesignUtils blueColor];
//                button.selectionHandler = ^(CNPPopupButton *button){
//                    [self.popupController dismissPopupControllerAnimated:YES];
//                };
//
//                UILabel *titleLabel = [[UILabel alloc] init];
//                titleLabel.numberOfLines = 0;
//                titleLabel.attributedText = title;
//                titleLabel.textColor = [DesignUtils blueColor];
//
//                UIScrollView *scrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(0, self.tabBarController.view.frame.size.height, [[UIScreen mainScreen] bounds].size.width*0.85, 100)];
//                scrollView.scrollEnabled = YES;
//                scrollView.backgroundColor = [UIColor whiteColor];
//
//                [scrollView setPagingEnabled:NO];
//                scrollView.showsHorizontalScrollIndicator = NO;
//
//                int multiply = 0;
//                for (NSString *image in dotImages1) {
//                    UIButton *button = [[UIButton alloc]initWithFrame:CGRectMake(10+(70*multiply), 20, 60, 60)];
//                    [button setImage:[UIImage imageNamed:image] forState:UIControlStateNormal];
//                    [button addTarget:self action:@selector(buttonChanged:) forControlEvents:UIControlEventTouchUpInside];
//                    button.tag = 300+multiply;
//                    [scrollView addSubview:button];
//                    multiply++;
//                }
//
//                scrollView.contentSize = CGSizeMake(10+(70*dotImages1.count), 100);
//                [scrollView setScrollEnabled:YES];
//
//                self.popupController = [[CNPPopupController alloc] initWithContents:@[titleLabel, scrollView, button]];
//                self.popupController.theme = [CNPPopupTheme defaultTheme];
//                self.popupController.theme.popupStyle = CNPPopupStyleActionSheet;
//                self.popupController.delegate = self;
//                [self.popupController presentPopupControllerAnimated:YES];
            }
        }
    }
}
