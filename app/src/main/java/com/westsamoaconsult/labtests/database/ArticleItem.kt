package com.westsamoaconsult.labtests.database

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.Optional
import java.util.*

@Serializable
data class ArticleItem(
    @Optional var itemId: Int = 0,
    val categories: List<Int>,
    val name: String,
    val type: Int,
    val address: String,
    @ContextualSerialization @Optional var lastOpened: Date = Date(0),
    @Optional val subtitle: String? = null,
    @Optional var isFavorite: Boolean = false,
    @Optional var link: String? = null,
    @Optional var pubmed: String? = null,
    @Optional val flag: String? = null,
    @Optional var Link0: MutableMap<Any, Any>? = null,
    @Optional val Link1: MutableMap<Any, Any>? = null,
    @Optional val Link2: MutableMap<Any, Any>? = null,
    @Optional val Link3: MutableMap<Any, Any>? = null,
    @Optional var emedicine: String? = null,
    @Optional var url: String? = null,
    @Optional var pubname: String? = null,
    @Optional var wikiname: String? = null,
    @Optional var emediname: String? = null,
    @Optional var gname: String? = null,
    @Optional var publogo: String? = null,
    @Optional var wikilogo: String? = null,
    @Optional var emedilogo: String? = null,
    @Optional var glogo: String? = null,
    @Optional val linkcount: Int? = null,
    @Optional val linkarray: MutableList<Any> = ArrayList()
) {
    fun initWithNode(node: Map<String, Map<Any, Any>?>, num: Int) {
        itemId = num

        linkcount?.let {
            for (i in 0 until it) {
                val link12 = String.format("Link%d", i)
                linkarray.add(node[link12] as MutableMap<Any, Any>)
            }
        }

        Link0?.let {
            pubmed = it["url"] as String
            pubname = it["name"] as String
            publogo = it["image"] as String
        }

        Link1?.let {
            link = it["url"] as String
            wikiname = it["name"] as String
            wikilogo = it["image"] as String
        }

        Link2?.let {
            emedicine = it["url"] as String
            emediname = it["name"] as String
            emedilogo = it["image"] as String
        }

        Link3?.let {
            url = it["url"] as String
            gname = it["name"] as String
            glogo = it["image"] as String
        }
    }
}