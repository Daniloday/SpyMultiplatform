package com.missclick.spy.core.advertising.di

import com.missclick.spy.core.advertising.AdMobIos
import com.missclick.spy.core.advertising.AdsProvider
import com.missclick.spy.core.advertising.AdsProviderResolver
import com.missclick.spy.core.advertising.AppLovinIos
import com.missclick.spy.core.advertising.InterstitialAdManager
import com.missclick.spy.core.advertising.InterstitialAdManagerIos
import com.missclick.spy.core.advertising.RateUs
import com.missclick.spy.core.advertising.RateUsIos
import com.missclick.spy.core.advertising.RewardedAdManager
import com.missclick.spy.core.advertising.RewardedAdManagerIos
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single { AdsProviderResolver() }

    factory<InterstitialAdManager> {
        when (get<AdsProviderResolver>().resolve()) {
            AdsProvider.ADMOB -> {
                val adMob = get<AdMobIos>()
                InterstitialAdManagerIos(
                    loadInterstitialAd = adMob.loadInterstitialAd,
                    showInterstitialAd = adMob.showInterstitialAd,
                )
            }

            AdsProvider.APPLOVIN -> {
                val appLovin = get<AppLovinIos>()
                InterstitialAdManagerIos(
                    loadInterstitialAd = appLovin.loadInterstitialAd,
                    showInterstitialAd = appLovin.showInterstitialAd,
                )
            }
        }
    }

    factory<RewardedAdManager> {
        when (get<AdsProviderResolver>().resolve()) {
            AdsProvider.ADMOB -> {
                val adMob = get<AdMobIos>()
                RewardedAdManagerIos(
                    loadRewardedAd = adMob.loadRewardedAd,
                    showRewardedAd = adMob.showRewardedAd,
                )
            }

            AdsProvider.APPLOVIN -> {
                val appLovin = get<AppLovinIos>()
                RewardedAdManagerIos(
                    loadRewardedAd = appLovin.loadRewardedAd,
                    showRewardedAd = appLovin.showRewardedAd,
                )
            }
        }
    }

    factory <RateUs> { RateUsIos() }
}
