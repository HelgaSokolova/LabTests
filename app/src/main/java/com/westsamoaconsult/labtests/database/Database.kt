package com.westsamoaconsult.labtests.database

import com.westsamoaconsult.labtests.utils.Constants
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*

@Serializable
data class DBItem (
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

        loadLastUsedTimes()
        loadFavorites()

        allArticlesSorted = allArticles.toMutableList();
        allArticlesSorted.sortBy { item -> item.name }

        allCategories = dbItem.Categories
        var i = 1
        for (item in allCategories) {
            item.autoId = i
            i++
        }
    }

    fun saveLastUsedTimes() {
        val values = mutableMapOf<String, Date>()
        for (item: ArticleItem in allArticles) {
            if (0 < item.lastOpened.compareTo(Date(0))) {
                values[String.format("%d", item.itemId)] = item.lastOpened
            }
        }

        Utils.saveData(Constants.Last_Opened_Times, values)
    }

    fun loadLastUsedTimes() {
        val values = Utils.loadData<Map<String, Date>>(Constants.Last_Opened_Times)
        for (item in allArticles) {
            if (values == null || values[String.format("%d", item.itemId)] == null) {
                item.lastOpened = Date(0)
            } else {
                item.lastOpened = values[String.format("%d", item.itemId)]!!
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
            if (0 < item.lastOpened.compareTo(Date(0))) {
                opened.add(item)
            }
        }
        opened.sortByDescending { item -> item.lastOpened }
        opened.dropLast(opened.size - 9)

        return opened;
    }

    fun getFavorites() : List<ArticleItem> {
        val opened = mutableListOf<ArticleItem>()

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