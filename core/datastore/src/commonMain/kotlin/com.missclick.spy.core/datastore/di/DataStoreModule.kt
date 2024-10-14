package com.missclick.spy.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.missclick.spy.core.datastore.OptionsDataSource
import com.missclick.spy.core.datastore.preferences.OptionsDataSourceImpl
import com.missclick.spy.core.datastore.preferences.OptionsPreferences
import com.missclick.spy.core.datastore.preferences.OptionsPreferencesSerializer
import okio.FileSystem
import okio.Path
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun platformModule(): Module

val dataStoreModule = module {
    single { OptionsPreferencesSerializer() }
    single<OptionsDataSource> { OptionsDataSourceImpl(get()) }
    includes(platformModule())
}

internal fun createDataStore(
    producePath: () -> Path,
    fileSystem: FileSystem,
    optionsPreferencesSerializer: OptionsPreferencesSerializer,
): DataStore<OptionsPreferences> =
    DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = fileSystem,
            producePath = producePath,
            serializer = optionsPreferencesSerializer,
        ),
    )

internal const val DATA_STORE_FILE_NAME = "datastore/options_preferences.pb"


