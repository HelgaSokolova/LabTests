package com.westsamoaconsult.labtests.info

abstract class InfoItem(
    val type: Int,
    val infoDescription: String) {

    class TYPE {
        companion object {
            const val SECTION = 0
            const val RANGE = 1
            const val TEXTSIZE = 2
            const val NORMAL = 3
            const val VERSION = 4
        }
    }
}

data class SectionInfoItem(
    val description: String
) : InfoItem(InfoItem.TYPE.SECTION, description)

class RangeInfoItem() : InfoItem(InfoItem.TYPE.RANGE, "")
