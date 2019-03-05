package com.westsamoaconsult.labtests.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.westsamoaconsult.labtests.R

object FragmentUtils {
    fun addFragment(fragment: Fragment, fragmentManager: FragmentManager, resId: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction().add(resId, fragment)
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
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
