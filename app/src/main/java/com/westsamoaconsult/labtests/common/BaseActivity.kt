package com.westsamoaconsult.labtests.common

import android.support.v7.app.AppCompatActivity
import android.view.View
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
    }

    fun setTitle(title: String) {
        actionBarTitle.text = title
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

    open fun onRightButtonPressed() {}
}
