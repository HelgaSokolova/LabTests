package com.westsamoaconsult.labtests.info

import android.widget.RadioGroup

abstract class InfoItem(
    val type: Int,
    val infoDescription: String) {

    companion object TYPE {
        val SECTION = 0
        val RANGE = 1
        val TEXTSIZE = 2
        val NORMAL = 3
        val VERSION = 4
    }
}

data class SectionInfoItem(
    val description: String
) : InfoItem(InfoItem.TYPE.SECTION, description)

class RangeInfoItem(
    val defaultCheckId: Int,
    val listener: RadioGroup.OnCheckedChangeListener
) : InfoItem(InfoItem.TYPE.RANGE, "")

class TextSizeInfoItem(
    val defaultCheckId: Int,
    val listener: RadioGroup.OnCheckedChangeListener
) : InfoItem(InfoItem.TYPE.TEXTSIZE, "")

class DefaultInfoItem(
    val description: String,
    val listener: InfoAdapter.OnItemClickListener
) : InfoItem(InfoItem.TYPE.NORMAL, description)