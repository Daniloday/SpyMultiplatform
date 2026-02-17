package com.missclick.spy.core.advertising.di

import com.missclick.spy.core.advertising.InterstitialAdManager
import com.missclick.spy.core.advertising.InterstitialAdManagerIos
import com.missclick.spy.core.advertising.RateUs
import com.missclick.spy.core.advertising.RateUsIos
import com.missclick.spy.core.advertising.RewardedAdManager
import com.missclick.spy.core.advertising.RewardedAdManagerIos
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    factory <InterstitialAdManager> { InterstitialAdManagerIos(get()) }
    factory <RewardedAdManager> { RewardedAdManagerIos(get()) }
    factory <RateUs> { RateUsIos() }
}