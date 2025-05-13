package com.missclick.spy

import android.app.Application
import com.applovin.sdk.AppLovinSdk
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.unity3d.ads.UnityAds
import com.unity3d.ads.metadata.MetaData

class SpyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        initAds()
    }

    private fun initAds() {
        MobileAds.initialize(this)
        UnityAds.initialize(applicationContext, BuildConfig.UNITY_GAME_ID, BuildConfig.DEBUG, null)
    }

}