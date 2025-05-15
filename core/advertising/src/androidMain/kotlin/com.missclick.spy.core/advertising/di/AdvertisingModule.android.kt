package com.missclick.spy.core.advertising.di

import com.missclick.spy.core.advertising.InterstitialAdManager
import com.missclick.spy.core.advertising.InterstitialAdManagerAdMobAndroid
import com.missclick.spy.core.advertising.RewardedAdManager
import com.missclick.spy.core.advertising.RewardedAdManagerAdMobAndroid
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single<InterstitialAdManager> {
        InterstitialAdManagerAdMobAndroid(
            context = get(),
            activity = inject()
        )
    }
    single<RewardedAdManager> {
        RewardedAdManagerAdMobAndroid(
            context = get(),
            activity = inject()
        )
    }
}