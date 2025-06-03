package com.missclick.spy

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.missclick.spy.di.appModule
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SpyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        initKoin()
        MobileAds.initialize(this)
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration.Builder(this, BuildConfig.PURCHASE_API_KEY).build())
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }
    }

}