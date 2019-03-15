package com.westsamoaconsult.labtests.info

import android.widget.RadioGroup

abstract class InfoItem(
    val type: Int) {

    companion object {
        const val SECTION = 0
        const val RANGE = 1
        const val TEXTSIZE = 2
        const val NORMAL = 3
        const val VERSION = 4
    }
}

class SectionInfoItem(
    val description: String
) : InfoItem(InfoItem.SECTION)

class RangeInfoItem(
    val defaultCheckId: Int,
    val listener: RadioGroup.OnCheckedChangeListener
) : InfoItem(InfoItem.RANGE)

class TextSizeInfoItem(
    val defaultCheckId: Int,
    val listener: RadioGroup.OnCheckedChangeListener
) : InfoItem(InfoItem.TEXTSIZE)

class DefaultInfoItem(
    val description: String,
    val listener: InfoAdapter.OnItemClickListener
) : InfoItem(InfoItem.NORMAL)