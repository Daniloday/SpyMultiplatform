package com.missclick.spy

import android.app.Application
import com.adapty.Adapty
import com.adapty.models.AdaptyConfig
import com.applovin.sdk.AppLovinSdk
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.unity3d.ads.UnityAds
import com.unity3d.ads.metadata.MetaData

class SpyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this)
//        Adapty.activate(
//            applicationContext,
//            AdaptyConfig.Builder(BuildConfig.ADAPTY_API_KEY).build()
//        )
    }

}