package com.westsamoaconsult.labtests.utils

import com.westsamoaconsult.labtests.MainApplication

class Utils {
    companion object {
        val settings by lazy { MainApplication.instance.getSharedPreferences(Constants.PREFS_NAME, 0) }
        val editor by lazy { settings.edit() }

        fun setGlobalTextSize(value: Int) = editor.putInt(Constants.GLOBAL_TEXT_SIZE, value).commit()
        fun getGlobalTextSize() = settings.getInt(Constants.GLOBAL_TEXT_SIZE, 16)

        fun setReferenceRange(value: String) = editor.putString(Constants.REFERENCE_RANGE, value).commit()
        fun getReferenceRange() = settings.getString(Constants.REFERENCE_RANGE, "SI")
    }
}