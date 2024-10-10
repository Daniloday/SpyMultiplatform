package com.missclick.spy.feature.game_options.di

import com.missclick.spy.feature.game_options.GameOptionsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val gameOptionsModule = module {
    viewModel { GameOptionsViewModel(get(), get()) }
}