package com.missclick.spy.core.advertising.di

import com.missclick.spy.core.advertising.InterstitialAdManager
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun platformModule(): Module

val advertisingModule = module {
    includes(platformModule())
}