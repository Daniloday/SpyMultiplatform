package com.missclick.spy.core.advertising.di

import com.missclick.spy.core.advertising.InterstitialAdManager
import com.missclick.spy.core.advertising.InterstitialAdManagerDesktop
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule() = module {
    single<InterstitialAdManager> { InterstitialAdManagerDesktop() }
}