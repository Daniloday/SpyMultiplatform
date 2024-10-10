package com.missclick.spy.feature.rules.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.missclick.spy.feature.rules.RulesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rulesModule = module {
    viewModel { RulesViewModel() }
}