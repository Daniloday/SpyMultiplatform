package com.missclick.spy

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.missclick.spy.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }

}