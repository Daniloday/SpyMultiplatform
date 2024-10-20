package com.missclick.spy

import android.app.Application
import com.google.android.gms.ads.MobileAds

class SpyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }

}