package com.westsamoaconsult.labtests.tabs.bookmark.detail

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.database.ArticleItem
import com.westsamoaconsult.labtests.utils.Constants
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.detail_change_color.view.*
import kotlinx.android.synthetic.main.info_fragment.*
import java.util.*


class DetailViewFragment: BaseFragment(), View.OnClickListener, ChangeColorAdapter.OnItemClickListener {
    private lateinit var bottomDialog: BottomSheetDialog
    private lateinit var itemList: MutableList<DetailViewItem>

    private lateinit var dataItem: ArticleItem
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private var data: MutableMap<String, Any>? = null;

    private val dotImages1 = arrayOf("Black.png", "Gold.png", "Gray.png", "Green.png", "Lavender.png", "Light Blue.png", "Light Green.png", "Orange.png", "Pink.png", "Red.png", "Royal Blue.png", "Tan.png", "White.png", "Yellow.png", "CSF.png", "Pico70.png");

    companion object {
        fun newInstance(articleId: Int) = DetailViewFragment().apply {
            arguments = Bundle().apply { putInt("articleId", articleId) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.westsamoaconsult.labtests.R.layout.bookmark_first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setBackgroundColor(Color.parseColor("#efeff4"))

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity!!);
        dataItem = MainApplication.instance.database.allArticles.first {
            item -> item.itemId == arguments!!.getInt("articleId")
        }

        bottomDialog = BottomSheetDialog(activity!!)
        val bottomSheet = layoutInflater.inflate(com.westsamoaconsult.labtests.R.layout.detail_change_color, null)
        bottomSheet.apply {
            btnCancel.setOnClickListener { bottomDialog.dismiss() }
            recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = ChangeColorAdapter(dotImages1, this@DetailViewFragment)
            bottomDialog.setContentView(this)
        }
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
                .setMessage("How would you like the Reference Range to be displayed?\r\n\r\n(It can be changed under 'More')")
                .setPositiveButton("SI") { dialog, _ ->
                    Utils.saveData(Constants.REFERENCE_RANGE, "SI"); loadHtmlFile(); dialog.dismiss()
                }
                .setNegativeButton("US") { dialog, _ ->
                    Utils.saveData(Constants.REFERENCE_RANGE, "US"); loadHtmlFile(); dialog.dismiss()
                }
                .show()
        }
        dataItem.lastOpened = Date()
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
        itemList = mutableListOf<DetailViewItem>()

        (data!!.get("Article") as ArrayList<*>).forEach {
            (it as Map<*, *>).apply {
                itemList.add(SectionDetailItem(get("header") as String))

                val paragraphList: ArrayList<*>
                if (Utils.loadData<String>(Constants.REFERENCE_RANGE) != "US") {
                    paragraphList = get("paragraph_SI") as ArrayList<*>
                } else {
                    paragraphList = get("paragraph_US") as ArrayList<*>
                }

                paragraphList.forEach{
                    val displayString = it as String

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
                            itemList.add(HeaderDetailItem(displayString, imageName, this@DetailViewFragment))
                        }
                        return@forEach
                    }

                    itemList.add(TextDetailItem(displayString))
                }
                itemList.add(FooterDetailItem())
            }
        }

        recyclerView.adapter = DetailViewAdapter(itemList)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            com.westsamoaconsult.labtests.R.id.btnImage -> {
                bottomDialog.show()
            }
        }
    }

    override fun onClick(color: String) {
        bottomDialog.dismiss()

        var dict = Utils.loadData<MutableMap<String, String>>("customDots")
        if (dict == null) {
            dict = mutableMapOf()
        }

        dict.set(dataItem.address, color)
        Utils.saveData("customDots", dict)
        buildList()
    }
}
