package com.westsamoaconsult.labtests.bookmark.detail

abstract class DetailViewItem(
    val type: Int) {
    companion object {
        const val SECTION = 0
        const val HEADER = 1
        const val IMAGE = 2
        const val TEXT = 3
    }
}

data class SectionDetailItem(
    val description: String,
    val isFirst: Boolean
) : DetailViewItem(SECTION)

data class HeaderDetailItem(
    val description: String,
    val imageName: String
) : DetailViewItem(HEADER)

data class ImageDetailItem(
    val imageName: String
) : DetailViewItem(IMAGE)

data class TextDetailItem(
    val description: String
) : DetailViewItem(TEXT)