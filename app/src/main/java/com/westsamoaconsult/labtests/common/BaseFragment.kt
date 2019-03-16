package com.westsamoaconsult.labtests.common

import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {
    open fun onForeground() {
        (activity as BaseActivity).apply {
            setTitle("")
            setBackButtonVisible(false)
            setRightButtonVisible(false, "")
            setSearchBarVisible(false)
        }
    }
    open fun onRightButtonPressed() {}
    open fun onSearchChanged(text: String) {}
}
