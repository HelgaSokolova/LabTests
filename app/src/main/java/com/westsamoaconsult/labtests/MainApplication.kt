package com.westsamoaconsult.labtests

import android.app.Application

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}
