package com.westsamoaconsult.labtests.bookmark

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.database.ArticleItem
import com.westsamoaconsult.labtests.utils.Constants
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.info_fragment.*
import kotlinx.serialization.json.Json
import java.util.*
import com.google.gson.reflect.TypeToken
import com.westsamoaconsult.labtests.R


class DetailViewFragment: BaseFragment(), SecondViewAdapter.OnItemClickListener {
    companion object {
        fun newInstance(article: ArticleItem) = DetailViewFragment().apply {
            arguments = Bundle().apply {
                putString("articleItem", Json.stringify(ArticleItem.serializer(), article))
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

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity!!);
        dataItem = Json.parse(ArticleItem.serializer(), arguments!!.getString("articleItem")!!)
    }

    override fun onClick(article: ArticleItem) {
    }

    override fun onForeground() {
        (activity as BaseActivity).apply {
            setTitle(dataItem.name)
            setBackButtonVisible(true)
            if (dataItem.isFavorite) {
                setRightButtonVisible(true, R.drawable.star_filled_22)
            } else {
                setRightButtonVisible(true, R.drawable.star_nonfilled_22)
            }
        }

        loadHtmlFile();
        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = SecondViewAdapter(context!!, articles, this)
    }

    override fun onRightButtonPressed() {
        if (dataItem.isFavorite) {
            dataItem.isFavorite = false
            (activity as BaseActivity).setRightButtonVisible(true, R.drawable.star_nonfilled_22)
        }
        else {
            dataItem.isFavorite = true
            (activity as BaseActivity).setRightButtonVisible(true, R.drawable.star_filled_22)
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
                    Utils.saveData(
                        Constants.REFERENCE_RANGE,
                        "SI"
                    ); loadHtmlFile(); dialog.dismiss()
                }
                .setNegativeButton("US") { dialog, _ ->
                    Utils.saveData(
                        Constants.REFERENCE_RANGE,
                        "US"
                    ); loadHtmlFile(); dialog.dismiss()
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

//        if (data == null) {
//            val db = Utils.loadJSONFromAsset(String.format("%s.json", dataItem.address));
//            val typeOfHashMap = object : TypeToken<MutableMap<String, Any>>() {}.type
//            data = Gson().fromJson<MutableMap<String, Any>>(db, typeOfHashMap)
//        }
    }
}
