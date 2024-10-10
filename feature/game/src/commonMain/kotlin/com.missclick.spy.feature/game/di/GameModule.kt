package com.missclick.spy.feature.game.di

import com.missclick.spy.feature.game.GameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val gameModule = module {
    viewModel { GameViewModel(get(), get()) }
}