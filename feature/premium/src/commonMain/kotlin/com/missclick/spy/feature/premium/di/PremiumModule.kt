package com.missclick.spy.feature.premium.di

import com.missclick.spy.feature.premium.PremiumViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val premiumModule = module {
    viewModel {
        PremiumViewModel(get(), get())
    }
}