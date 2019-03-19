package com.westsamoaconsult.labtests.common

import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.main_activity.*

open class BaseFragment : Fragment() {
    var isLocked = false

    open fun onForeground() {
        isLocked = false
        (activity as BaseActivity).apply {
            setTitle("")
            setBackButtonVisible(false)
            setRightButtonVisible(false, "")
            setSearchBarVisible(false)

            rightActionBar?.let {
                setTitle2("")
                setBackButtonVisible2(false)
                setRightButtonVisible2(false)
            }
        }
    }
    open fun onRightButtonPressed() {}
    open fun onSearchChanged(text: String) {}

    fun setLockScreen(isLock: Boolean = true) {
        this.isLocked = isLock
    }
}
