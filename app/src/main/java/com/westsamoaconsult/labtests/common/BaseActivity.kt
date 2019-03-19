package com.westsamoaconsult.labtests.common

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import com.westsamoaconsult.labtests.R
import kotlinx.android.synthetic.main.action_bar.view.*
import kotlinx.android.synthetic.main.detail_item_text.*
import kotlinx.android.synthetic.main.main_activity.*

open class BaseActivity : AppCompatActivity() {
    var searchText = ""
    override fun onResume() {
        super.onResume()

        val fragment = supportFragmentManager!!.findFragmentById(R.id.fragmentLeftContainer)
        fragment?.let {
            (fragment as BaseFragment).onForeground()
        }

        val fragment2 = supportFragmentManager!!.findFragmentById(R.id.fragmentRightContainer)
        fragment2?.let {
            (fragment2 as BaseFragment).onForeground()
        }

        leftActionBar.apply {
            btnLeft.setOnClickListener {onLeftButtonPressed() }
            btnRight.setOnClickListener { onRightButtonPressed() }
            actionBarClose.setOnClickListener { clearSearchBarText() }
            actionBarSearchText.afterTextChanged {
                if (!searchText.equals(it)) {
                    onSearchChanged(it)
                    searchText = it
                }
            }
        }

        rightActionBar?.apply {
            btnLeft.setOnClickListener{ onLeftButtonPressed2() }
            btnRight.setOnClickListener{ onRightButtonPressed2() }
        }
    }

    fun setTitle(title: String, visible: Boolean = true) {
        leftActionBar.actionBarTitle.text = title
        leftActionBar.actionBarTitle.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setBackButtonVisible(visible: Boolean) {
        leftActionBar.btnLeft.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setRightButtonVisible(visible: Boolean, label: String) {
        if (visible) {
            leftActionBar.btnRight.visibility = View.VISIBLE
            leftActionBar.btnRightImage.visibility = View.GONE
            leftActionBar.btnRightText.visibility = View.VISIBLE
            leftActionBar.btnRightText.text = label
        } else {
            leftActionBar.btnRight.visibility = View.GONE
        }
    }

    fun setRightButtonVisible(visible: Boolean, iconRes: Int) {
        if (visible) {
            leftActionBar.btnRight.visibility = View.VISIBLE
            leftActionBar.btnRightText.visibility = View.GONE
            leftActionBar.btnRightImage.visibility = View.VISIBLE
            leftActionBar.btnRightImage.setImageResource(iconRes)
        } else {
            leftActionBar.btnRight.visibility = View.GONE
        }
    }

    fun setSearchBarVisible(visible: Boolean) {
        if (visible) {
            leftActionBar.actionBarTitle.visibility = View.GONE
            leftActionBar.rightLayout.layoutParams = leftActionBar.rightLayout.layoutParams.apply { width = LinearLayout.LayoutParams.MATCH_PARENT }
            leftActionBar.actionBarSearch.visibility = View.VISIBLE
        } else {
            leftActionBar.actionBarTitle.visibility = View.VISIBLE
            leftActionBar.rightLayout.layoutParams = leftActionBar.rightLayout.layoutParams.apply { width = LinearLayout.LayoutParams.WRAP_CONTENT }
            leftActionBar.actionBarSearch.visibility = View.GONE
        }
    }

    fun clearSearchBarText() {
        leftActionBar.actionBarSearchText.setText("")
    }

    fun getSearchBarVisible() = leftActionBar.actionBarSearch.visibility == View.VISIBLE

    fun setTitle2(title: String, visible: Boolean = true) {
        rightActionBar?.apply {
            actionBarTitle.text = title
            actionBarTitle.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

    fun setBackButtonVisible2(visible: Boolean, iconRes: Int = -1) {
        rightActionBar?.btnLeft?.visibility = if (visible) View.VISIBLE else View.GONE
        if (visible) {
            rightActionBar?.apply {
                btnLeftImage.setImageResource(if (iconRes != -1) iconRes else R.drawable.back)
            }
        }
    }

    fun setRightButtonVisible2(visible: Boolean, iconRes: Int = -1) {
        if (visible) {
            rightActionBar?.apply {
                btnRight.visibility = View.VISIBLE
                btnRightText.visibility = View.GONE
                btnRightImage.visibility = View.VISIBLE
                btnRightImage.setImageResource(iconRes)
            }
        } else {
            rightActionBar?.btnRight?.visibility = View.GONE
        }
    }

    open fun onLeftButtonPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentLeftContainer)
        fragment?.let {
            (fragment as BaseFragment).onLeftButtonPressed()
        }
    }

    open fun onLeftButtonPressed2() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentRightContainer)
        fragment?.let {
            (fragment as BaseFragment).onLeftButtonPressed2()
        }
    }

    open fun onRightButtonPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentLeftContainer)
        fragment?.let {
            (fragment as BaseFragment).onRightButtonPressed()
        }
    }

    open fun onRightButtonPressed2() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentRightContainer)
        fragment?.let {
            (fragment as BaseFragment).onRightButtonPressed2()
        }
    }

    open fun onSearchChanged(text: String) {
        if (text.isNotEmpty()) {
            leftActionBar.actionBarClose.visibility = View.VISIBLE
        } else {
            leftActionBar.actionBarClose.visibility = View.GONE
        }

        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentLeftContainer)
        fragment?.let {
            (fragment as BaseFragment).onSearchChanged(text)
        }
    }
}
