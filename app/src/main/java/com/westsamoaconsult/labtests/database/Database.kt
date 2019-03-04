package com.westsamoaconsult.labtests.database

import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class dbItem (
    val Articles: List<ArticleItem>,
    val Categories: List<CategoryItem>
)

class Database {
    companion object {
        lateinit var allArticles: List<Any>
    }

    init {
        val db = Utils.loadJSONFromAsset("mainDatabase.json")

        val dbItem = Json.nonstrict.parse(dbItem.serializer(), db)
        allArticles = dbItem.Articles
    }
}