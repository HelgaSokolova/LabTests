package com.westsamoaconsult.labtests.database

import com.westsamoaconsult.labtests.utils.Constants
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*

@Serializable
class DBItem (
    val Articles: List<ArticleItem>,
    val Categories: List<CategoryItem>
)

class Database {
    var allArticles: List<ArticleItem>
    var allArticlesSorted: MutableList<ArticleItem>
    var allCategories: List<CategoryItem>

    init {
        val db = Utils.loadJSONFromAsset("mainDatabase.json")

        val dbItem = Json.nonstrict.parse(DBItem.serializer(), db)
        allArticles = dbItem.Articles
        var i = 1
        allArticles.forEach {
            it.initWithNode(i++)
        }

        loadLastUsedTimes()
        loadFavorites()

        allArticlesSorted = allArticles.toMutableList();
        allArticlesSorted.sortBy { item -> item.name }

        allCategories = dbItem.Categories
        i = 1
        for (item in allCategories) {
            item.autoId = i
            i++
        }
    }

    fun saveLastUsedTimes() {
        val values = mutableMapOf<String, Long>()
        for (item: ArticleItem in allArticles) {
            if (item.lastOpened!! > Date(0)) {
                values[String.format("%d", item.itemId)] = item.lastOpened!!.time
            }
        }

        Utils.saveData(Constants.Last_Opened_Times, values)
    }

    private fun loadLastUsedTimes() {
        val values = Utils.loadData<Map<String, Long>>(Constants.Last_Opened_Times)
        for (item in allArticles) {
            if (values == null || values[String.format("%d", item.itemId)] == null) {
                item.lastOpened = Date(0)
            } else {
                item.lastOpened = Date(values[String.format("%d", item.itemId)]!!)
            }
        }
    }

    fun clearRecents() {
        Utils.saveData(Constants.Last_Opened_Times, mapOf<String, Date>())
        for (item: ArticleItem in allArticles) {
            item.lastOpened = Date(0)
        }
    }

    fun saveFavorites() {
        val values = mutableMapOf<String, Boolean>()
        for (item: ArticleItem in allArticles) {
            if (item.isFavorite) {
                values[String.format("%d", item.itemId)] = true
            }
        }

        Utils.saveData(Constants.Favorites, values)
    }

    fun loadFavorites() {
        val values = Utils.loadData<Map<String, Boolean>>(Constants.Favorites)
        for (item in allArticles) {

            if (values == null || values[String.format("%d", item.itemId)] == null) {
                item.isFavorite = false
            } else {
                item.isFavorite = values[String.format("%d", item.itemId)] as Boolean
            }
        }
    }

    fun getRecents() : List<ArticleItem> {
        val opened = mutableListOf<ArticleItem>()

        for (item in allArticles) {
            if (item.lastOpened!! > Date(0)) {
                opened.add(item)
            }
        }
        opened.sortByDescending { item -> item.lastOpened }
        if (opened.size > 10) {
            opened.dropLast(opened.size - 9)
        }

        return opened;
    }

    fun getFavorites() : ArrayList<ArticleItem> {
        val opened = arrayListOf<ArticleItem>()

        for (item in allArticles) {
            if (item.isFavorite) {
                opened.add(item)
            }
        }
        opened.sortBy { item -> item.name }

        return opened;
    }

    fun getItemsOfCategory(cat: Int) : List<ArticleItem> {
        val found = mutableListOf<ArticleItem>()

        for (item in allArticles) {
            for (categoryNum in item.categories) {
                if (categoryNum == cat) {
                    found.add(item)
                }
            }
        }

        found.sortBy { item -> item.name }
        return found;
    }
}