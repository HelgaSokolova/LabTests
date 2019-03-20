package com.westsamoaconsult.labtests

import android.os.Bundle
import com.westsamoaconsult.labtests.common.BaseActivity
import kotlinx.android.synthetic.main.activity_disclaimer.*

class DisclaimerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disclaimer)

        webView.loadUrl("file:///android_asset/infotext.htm")
    }

    override fun onResume() {
        super.onResume()

        setTitle("Disclaimer", true)
        setBackButtonVisible(true)
    }
}
