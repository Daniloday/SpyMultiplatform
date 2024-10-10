package com.missclick.spy.feature.collections.di

import com.missclick.spy.feature.collections.CollectionsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val collectionsModule = module {

    viewModel { CollectionsViewModel(get(), get()) }

}