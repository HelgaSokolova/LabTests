package com.westsamoaconsult.labtests.utils

import com.google.gson.Gson
import com.westsamoaconsult.labtests.MainApplication
import kotlin.text.Charsets.UTF_8

class Utils {
    companion object {
        val settings by lazy { MainApplication.instance.getSharedPreferences(Constants.PREFS_NAME, 0) }
        val editor by lazy { settings.edit() }

        fun saveData(key: String, value: String) = editor.putString(key, value).commit()
        fun loadStringData(key: String) = settings.getString(key, "")

        fun saveData(key: String, value: Int) = editor.putInt(key, value).commit()
        fun loadIntData(key: String) : Int = settings.getInt(key, -1)

        fun saveData(key: String, value: Any) = saveData(key, Gson().toJson(value))
        @Suppress("UNCHECKED_CAST")
        inline fun <reified T: Any> loadData(key: String) = Gson().fromJson(loadStringData(key), T::class.java)

        fun loadJSONFromAsset(fileName: String): String {
            val inputStream = MainApplication.instance.getAssets().open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            return String(buffer, UTF_8)
        }
    }
}