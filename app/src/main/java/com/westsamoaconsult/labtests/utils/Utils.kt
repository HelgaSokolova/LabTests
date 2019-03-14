package com.westsamoaconsult.labtests.utils

import android.app.Activity
import android.content.SharedPreferences
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import com.google.gson.Gson
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.R
import kotlin.text.Charsets.UTF_8

object Utils {
    val settings: SharedPreferences by lazy { MainApplication.instance.getSharedPreferences(Constants.PREFS_NAME, 0) }
    private val editor: SharedPreferences.Editor by lazy { settings.edit() }

    fun saveData(key: String, value: Any) = editor.putString(key, Gson().toJson(value)).commit()
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T: Any> loadData(key: String): T? = Gson().fromJson(settings.getString(key, ""), T::class.java)

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

    fun showAlertDialog(activity: Activity, title: String? = null, message: String) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}