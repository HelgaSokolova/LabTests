package com.westsamoaconsult.labtests.components

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.westsamoaconsult.labtests.utils.Utils

class DynamicSizeTextView : TextView {
    constructor(context: Context) : super(context) {
        this.textSize = Utils.getGlobalTextSize().toFloat()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.textSize = Utils.getGlobalTextSize().toFloat()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.textSize = Utils.getGlobalTextSize().toFloat()
    }
}