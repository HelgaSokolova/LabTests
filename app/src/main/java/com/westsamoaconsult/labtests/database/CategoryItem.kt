package com.westsamoaconsult.labtests.database

data class CategoryItem(
    var autoId: Int,
    val description: String?,
    val logo: String?,
    val name: String?,
    val parrents: List<Int>?
)