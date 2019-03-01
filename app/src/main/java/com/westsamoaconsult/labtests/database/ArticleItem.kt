package com.westsamoaconsult.labtests.database

import java.util.*

data class ArticleItem(
    var itemId: Int?,
    val htmlText: String?,
    val categories: List<CategoryItem>?,
    val name: String?,
    val lastOpened: Date = Date(0),
    val type: Int?,
    val address: String?,
    val subtitle: String?,
    val isFavorite: Int?,
    var link: String?,
    var pubmed: String?,
    val flag: String?,
    var Link0: MutableMap<Any, Any>,
    val Link1: MutableMap<Any, Any>,
    val Link2: MutableMap<Any, Any>,
    val Link3: MutableMap<Any, Any>,
    var emedicine: String?,
    var url: String?,
    var pubname: String?,
    var wikiname: String?,
    var emediname: String?,
    var gname: String?,
    var publogo: String?,
    var wikilogo: String?,
    var emedilogo: String?,
    var glogo: String?,
    val linkcount: Int?,
    val linkarray: MutableList<Any> = ArrayList()
) {
    fun initWithNode(node: Map<String, Map<Any, Any>?>, i: Int) {
        itemId = i

        linkcount?.let {
            for (i in 0..(it - 1)) {
                val link12 = String.format("Link%d", i)
                linkarray.add(node[link12] as MutableMap<Any, Any>)
            }
        }

        pubmed = Link0["url"] as String
        pubname = Link0["name"] as String
        publogo = Link0["image"] as String

        link = Link1["url"] as String
        wikiname = Link1["name"] as String
        wikilogo = Link1["image"] as String

        emedicine = Link2["url"] as String
        emediname = Link2["name"] as String
        emedilogo = Link2["image"] as String

        url = Link3["url"] as String
        gname = Link3["name"] as String
        glogo = Link3["image"] as String

    }
}