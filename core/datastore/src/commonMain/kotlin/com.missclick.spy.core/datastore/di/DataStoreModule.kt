package com.missclick.spy.core.datastore.di

import com.missclick.spy.core.datastore.OptionsDataSource
import com.missclick.spy.core.datastore.preferences.OptionsDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun platformModule(): Module

val dataStoreModule = module {
    single<OptionsDataSource> { OptionsDataSourceImpl(get()) }
    includes(platformModule())
}

internal const val DATA_STORE_FILE_NAME = "options_preferences.pb"


