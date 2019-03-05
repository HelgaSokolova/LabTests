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
        setProperties(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setProperties(attrs)
    }

    fun setProperties(attrs: AttributeSet? = null) {
        var textColor = "#616161"
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, com.westsamoaconsult.labtests.R.styleable.DynamicSizeTextView)
            try {
                textColor = typedArray.getText(com.westsamoaconsult.labtests.R.styleable.DynamicSizeTextView_android_textColor).toString()
            } catch (e: Exception) {
            }
        }

        this.textSize = Utils.loadIntData(Constants.GLOBAL_TEXT_SIZE).toFloat()
        this.setTextColor(Color.parseColor(textColor))
    }
}