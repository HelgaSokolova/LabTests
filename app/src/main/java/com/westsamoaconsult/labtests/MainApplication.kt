package com.westsamoaconsult.labtests

import android.app.Application
import com.westsamoaconsult.labtests.database.Database

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
            private set
    }

    lateinit var database: Database

    override fun onCreate() {
        super.onCreate()

        instance = this
        database = Database()
    }
}
