package com.westsamoaconsult.labtests.database

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
class CategoryItem(
    @Optional var autoId: Int = 0,
    val description: String,
    val logo: String,
    val name: String,
    val parrents: List<Int>
)