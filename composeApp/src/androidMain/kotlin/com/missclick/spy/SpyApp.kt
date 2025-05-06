package com.missclick.spy

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.unity3d.ads.UnityAds

class SpyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        UnityAds.initialize(applicationContext, BuildConfig.UNITY_GAME_ID, BuildConfig.DEBUG, null)
    }

}