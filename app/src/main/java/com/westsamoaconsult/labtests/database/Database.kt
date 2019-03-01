package com.westsamoaconsult.labtests.database

import android.util.Log
import com.westsamoaconsult.labtests.utils.Utils
import org.json.JSONException
import org.json.JSONObject


class Database {
    companion object {
        lateinit var allArticles: List<Any>
    }

    constructor() {
        val db = Utils.loadJSONFromAsset()



        val obj = JSONObject(db)
        val m_jArry = obj.getJSONArray("formules")
        val formList = ArrayList<HashMap<String, String>>()
        var m_li: HashMap<String, String>

        for (i in 0 until m_jArry.length()) {
            val jo_inside = m_jArry.getJSONObject(i)
            Log.d("Details-->", jo_inside.getString("formule"))
            val formula_value = jo_inside.getString("formule")
            val url_value = jo_inside.getString("url")

            //Add your values in your `ArrayList` as below:
            m_li = HashMap()
            m_li["formule"] = formula_value
            m_li["url"] = url_value

            formList.add(m_li)
        }
    }
}