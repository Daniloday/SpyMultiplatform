package com.missclick.spy.feature.sets.di

import com.missclick.spy.feature.sets.CollectionsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val collectionsModule = module {

    viewModel { CollectionsViewModel(get(), get(), get()) }

}