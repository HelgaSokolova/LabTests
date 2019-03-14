package com.westsamoaconsult.labtests

import android.app.Application
import com.westsamoaconsult.labtests.database.Database
import com.westsamoaconsult.labtests.database.FBDatabase
import com.westsamoaconsult.labtests.utils.Constants
import com.westsamoaconsult.labtests.utils.Utils

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
            private set
    }
    lateinit var database: Database

    lateinit var FBDatabase: FBDatabase
    override fun onCreate() {
        super.onCreate()

        instance = this

        database = Database()
        FBDatabase = FBDatabase()
    }
}
