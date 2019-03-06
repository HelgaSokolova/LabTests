package com.westsamoaconsult.labtests.common

import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.action_bar.*

open class BaseActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun setTitle(title: String) {
        actionBarTitle.text = title
    }

    fun setBackButtonVisible(visible: Boolean) {
        if (visible) {
            btnBack.visibility = View.VISIBLE
        } else {
            btnBack.visibility = View.GONE
        }
    }
}
