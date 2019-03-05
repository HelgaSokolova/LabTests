package com.westsamoaconsult.labtests.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.google.gson.Gson
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.R
import kotlin.text.Charsets.UTF_8

object Utils {
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

    fun addFragment(fragment: Fragment, fragmentManager: FragmentManager, resId: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.add(resId, fragment)
        fragmentTransaction.commit()
    }

    fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager, resId: Int, tag: String = "", backStackNeeded: Boolean = false): Boolean {
        val fragmentTransaction = fragmentManager.beginTransaction().replace(resId, fragment)
        if (backStackNeeded) {
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commit()
        return true
    }
}