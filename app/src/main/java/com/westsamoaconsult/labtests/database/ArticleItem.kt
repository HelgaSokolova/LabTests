package com.westsamoaconsult.labtests.database

import kotlinx.serialization.*
import kotlinx.serialization.Optional
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Serializable
class ArticleItem(
    @Optional var itemId: Int = 0,
    val categories: List<Int>,
    val name: String,
    val type: Int,
    val address: String,
    @ContextualSerialization @Optional var lastOpened: Date? = Date(0),
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
    fun initWithNode(num: Int) {
        itemId = num
    }
}

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    private val df: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS")

    override fun serialize(encoder: Encoder, obj: Date) {
        encoder.encodeString(df.format(obj))
    }

    override fun deserialize(decoder: Decoder): Date {
        return df.parse(decoder.decodeString())
    }
}