package com.westsamoaconsult.labtests.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.TextView
import com.westsamoaconsult.labtests.utils.Constants
import com.westsamoaconsult.labtests.utils.Utils

class DynamicSizeTextView : TextView {
    constructor(context: Context) : super(context) {
        setProperties()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setProperties()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setProperties()
    }

    fun setProperties() {
        this.textSize = Utils.loadIntData(Constants.GLOBAL_TEXT_SIZE).toFloat()
        this.setTextColor(Color.parseColor("#616161"))
    }
}