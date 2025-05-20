package com.missclick.spy

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration

class SpyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this)
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration.Builder(this, BuildConfig.PURCHASE_API_KEY).build())
    }

}