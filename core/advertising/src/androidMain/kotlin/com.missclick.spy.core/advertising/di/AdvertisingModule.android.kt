package com.missclick.spy.core.advertising.di

import androidx.compose.ui.text.intl.Locale
import com.missclick.spy.core.advertising.InterstitialAdManager
import com.missclick.spy.core.advertising.InterstitialAdManagerAdMobAndroid
import com.missclick.spy.core.advertising.InterstitialAdManagerUnityAndroid
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single<InterstitialAdManager> {
        if (Locale.current.region.lowercase() == "ru") {
            InterstitialAdManagerUnityAndroid(
                context = get(),
                activity = inject()
            )
        } else {
            InterstitialAdManagerAdMobAndroid(
                context = get(),
                activity = inject()
            )
        }
    }
}