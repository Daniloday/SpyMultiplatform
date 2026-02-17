package com.missclick.spy

import android.app.Application
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.missclick.spy.di.appModule
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SpyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        initKoin()
        MobileAds.initialize(this)
        val initConfig = AppLovinSdkInitializationConfiguration.builder(BuildConfig.APP_LOVIN_APPLICATION_ID)
            .setMediationProvider(AppLovinMediationProvider.MAX)
            .build()

        AppLovinSdk.getInstance(this).initialize(initConfig) {}
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration.Builder( BuildConfig.PURCHASE_API_KEY).build())
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }
    }

}