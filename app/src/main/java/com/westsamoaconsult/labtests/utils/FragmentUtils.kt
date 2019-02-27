package com.westsamoaconsult.labtests.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

object FragmentUtils {
    fun addFragment(fragment: Fragment, fragmentManager: FragmentManager, resId: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction().add(resId, fragment)
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
