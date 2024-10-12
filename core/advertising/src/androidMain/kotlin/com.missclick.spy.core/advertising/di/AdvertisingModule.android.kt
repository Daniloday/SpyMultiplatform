package com.missclick.spy.core.advertising.di

import com.missclick.spy.core.advertising.InterstitialAdManager
import com.missclick.spy.core.advertising.InterstitialAdManagerAndroid
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single<InterstitialAdManager> { InterstitialAdManagerAndroid(
        context = get(),
        activity = inject()
    ) }
}