package com.westsamoaconsult.labtests.common

import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {
    var isLocked = false

    open fun onForeground() {
        isLocked = false
        (activity as BaseActivity).apply {
            setTitle("")
            setBackButtonVisible(false)
            setRightButtonVisible(false, "")
            setSearchBarVisible(false)
        }
    }
    open fun onLeftButtonPressed() {
        activity!!.onBackPressed()
    }
    open fun onLeftButtonPressed2() {}
    open fun onRightButtonPressed() {}
    open fun onRightButtonPressed2() {}
    open fun onSearchChanged(text: String) {}

    fun setLockScreen(isLock: Boolean = true) {
        this.isLocked = isLock
    }
}
