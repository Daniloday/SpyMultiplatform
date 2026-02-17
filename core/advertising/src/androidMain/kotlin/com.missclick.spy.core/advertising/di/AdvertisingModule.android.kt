package com.missclick.spy.core.advertising.di

import android.app.Activity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.missclick.spy.core.advertising.AdsProvider
import com.missclick.spy.core.advertising.AdsProviderResolver
import com.missclick.spy.core.advertising.InterstitialAdManager
import com.missclick.spy.core.advertising.RateUs
import com.missclick.spy.core.advertising.RateUsAndroid
import com.missclick.spy.core.advertising.interstitial.InterstitialAdManagerAdMobAndroid
import com.missclick.spy.core.advertising.RewardedAdManager
import com.missclick.spy.core.advertising.interstitial.InterstitialAdManagerAppLovinAndroid
import com.missclick.spy.core.advertising.rewarded.RewardedAdManagerAdMobAndroid
import com.missclick.spy.core.advertising.rewarded.RewardedAdManagerAppLovinAndroid
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {

    factory <RateUs> { (activity: Activity) -> RateUsAndroid(activity) }

    single { FirebaseRemoteConfig.getInstance() }

    single {
        AdsProviderResolver(
            context = get(),
            remoteConfig = get()
        )
    }

    factory<InterstitialAdManager> { (activity: Activity) ->
        when (get<AdsProviderResolver>().resolve()) {
            AdsProvider.ADMOB ->
                InterstitialAdManagerAdMobAndroid(
                    context = get(),
                    activity = activity
                )

            AdsProvider.APPLOVIN ->
                InterstitialAdManagerAppLovinAndroid(
                    activity = activity
                )
        }
    }

    factory<RewardedAdManager> { (activity: Activity) ->
        when (get<AdsProviderResolver>().resolve()) {
            AdsProvider.ADMOB ->
                RewardedAdManagerAdMobAndroid(
                    context = get(),
                    activity = activity
                )

            AdsProvider.APPLOVIN ->
                RewardedAdManagerAppLovinAndroid(
                    activity = activity
                )
        }
    }

}