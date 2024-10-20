package com.missclick.spy.feature.settings.di

import com.missclick.spy.feature.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val settingsModule = module {
    viewModel { SettingsViewModel(get(), get(), get(), get()) }
}