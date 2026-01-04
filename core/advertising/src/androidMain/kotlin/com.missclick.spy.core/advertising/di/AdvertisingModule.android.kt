package com.missclick.spy.core.advertising.di

import android.app.Activity
import com.missclick.spy.core.advertising.InterstitialAdManager
import com.missclick.spy.core.advertising.interstitial.InterstitialAdManagerAdMobAndroid
import com.missclick.spy.core.advertising.RewardedAdManager
import com.missclick.spy.core.advertising.rewarded.RewardedAdManagerAdMobAndroid
import com.missclick.spy.core.advertising.rewarded.RewardedAdManagerAppLovinAndroid
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    factory<InterstitialAdManager> { (activity: Activity) ->
        InterstitialAdManagerAdMobAndroid(
            context = get(),
            activity = activity
        )
    }

//    factory<InterstitialAdManager> { (activity: Activity) ->
//        InterstitialAdManagerAppLovinAndroid(
//            activity = activity
//        )
//    }


    factory<RewardedAdManager> { (activity: Activity) ->
        RewardedAdManagerAdMobAndroid(
            context = get(),
            activity = activity
        )
    }

//    factory<RewardedAdManager> { (activity: Activity) ->
//        RewardedAdManagerAppLovinAndroid(
//            activity = activity
//        )
//    }
}