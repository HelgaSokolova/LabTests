package com.westsamoaconsult.labtests.common

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.action_bar.*

open class BaseActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()

        btnLeft.setOnClickListener {
            onBackPressed()
        }
        btnRight.setOnClickListener{
            onRightButtonPressed()
        }
        actionBarClose.setOnClickListener {
            actionBarSearchText.setText("")
        }
        actionBarSearchText.afterTextChanged {
            onSearchChanged(it)
        }
    }

    fun setTitle(title: String, visible: Boolean = true) {
        actionBarTitle.text = title
        if (visible) {
            actionBarTitle.visibility = View.VISIBLE
        } else {
            actionBarTitle.visibility = View.GONE
        }
    }

    fun setBackButtonVisible(visible: Boolean) {
        if (visible) {
            btnLeft.visibility = View.VISIBLE
        } else {
            btnLeft.visibility = View.GONE
        }
    }

    fun setRightButtonVisible(visible: Boolean, label: String) {
        if (visible) {
            btnRight.visibility = View.VISIBLE
            btnRightImage.visibility = View.GONE
            btnRightText.visibility = View.VISIBLE
            btnRightText.text = label
        } else {
            btnRight.visibility = View.GONE
        }
    }

    fun setRightButtonVisible(visible: Boolean, iconRes: Int) {
        if (visible) {
            btnRight.visibility = View.VISIBLE
            btnRightText.visibility = View.GONE
            btnRightImage.visibility = View.VISIBLE
            btnRightImage.setImageResource(iconRes)
        } else {
            btnRight.visibility = View.GONE
        }
    }

    fun setSearchBarVisible(visible: Boolean) {
        if (visible) {
            actionBarTitle.visibility = View.GONE
            rightLayout.layoutParams = rightLayout.layoutParams.apply { width = LinearLayout.LayoutParams.MATCH_PARENT }
            actionBarSearch.visibility = View.VISIBLE
        } else {
            actionBarSearchText.setText("")
            actionBarTitle.visibility = View.VISIBLE
            rightLayout.layoutParams = rightLayout.layoutParams.apply { width = LinearLayout.LayoutParams.WRAP_CONTENT }
            actionBarSearch.visibility = View.GONE
        }
    }

    fun getSearchBarVisible() = actionBarSearch.visibility == View.VISIBLE

    open fun onRightButtonPressed() {}

    open fun onSearchChanged(text: String) {
        if (text.isNotEmpty()) {
            actionBarClose.visibility = View.VISIBLE
        } else {
            actionBarClose.visibility = View.GONE
        }
    }
}
