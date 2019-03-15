package com.westsamoaconsult.labtests.bookmark.detail

import android.view.View

abstract class DetailViewItem(
    val type: Int) {
    companion object {
        const val SECTION = 0
        const val HEADER = 1
        const val IMAGE = 2
        const val TEXT = 3
        const val FOOTER = 4
    }
}

class SectionDetailItem(
    val description: String
) : DetailViewItem(SECTION)

class HeaderDetailItem(
    val description: String,
    val imageName: String,
    val listener: View.OnClickListener
) : DetailViewItem(HEADER)

class ImageDetailItem(
    val imageName: String
) : DetailViewItem(IMAGE)

class TextDetailItem(
    val description: String
) : DetailViewItem(TEXT)

class FooterDetailItem() : DetailViewItem(FOOTER)