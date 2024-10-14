package com.missclick.spy.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.missclick.spy.core.datastore.OptionsDataSource
import com.missclick.spy.core.datastore.preferences.OptionsDataSourceImpl
import com.missclick.spy.core.datastore.preferences.OptionsPreferences
import com.missclick.spy.core.datastore.preferences.OptionsPreferencesSerializer
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module


internal actual fun platformModule(): Module = module {
    single { provideDataStore(get(), get()) }
}

private fun provideDataStore(
    context: Context,
    optionsPreferencesSerializer: OptionsPreferencesSerializer,
): DataStore<OptionsPreferences> {

    val producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath() }

    return createDataStore(
        fileSystem = FileSystem.SYSTEM,
        producePath = producePath,
        optionsPreferencesSerializer = optionsPreferencesSerializer
    )
}