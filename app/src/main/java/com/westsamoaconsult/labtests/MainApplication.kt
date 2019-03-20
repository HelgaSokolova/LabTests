package com.westsamoaconsult.labtests

import android.app.Application
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.westsamoaconsult.labtests.database.Database
import com.westsamoaconsult.labtests.database.FBDatabase
import com.westsamoaconsult.labtests.utils.Constants


class MainApplication : Application(), BillingProcessor.IBillingHandler {
    companion object {
        lateinit var instance: MainApplication
            private set
    }
    lateinit var database: Database
    lateinit var FBDatabase: FBDatabase
    lateinit var bp: BillingProcessor

    override fun onCreate() {
        super.onCreate()
        instance = this

        database = Database()
        FBDatabase = FBDatabase()
//        bp = BillingProcessor(this, Constants.APP_LICENSE_KEY, this)
//        bp.initialize()
    }

    override fun onBillingInitialized() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPurchaseHistoryRestored() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProductPurchased(productId: String, details: TransactionDetails?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
