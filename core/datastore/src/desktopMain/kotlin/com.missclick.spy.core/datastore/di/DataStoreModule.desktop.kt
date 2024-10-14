package com.missclick.spy.core.datastore.di

import androidx.datastore.core.DataStore
import com.missclick.spy.core.datastore.preferences.OptionsPreferences
import com.missclick.spy.core.datastore.preferences.OptionsPreferencesSerializer
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.koin.core.module.Module
import org.koin.dsl.module
import java.nio.file.Paths

internal actual fun platformModule(): Module = module {
    single { provideDataStore(get()) }
}

private fun provideDataStore(
    optionsPreferencesSerializer: OptionsPreferencesSerializer,
): DataStore<OptionsPreferences> {
    val userHome = System.getProperty("user.home")
    val path = Paths.get(userHome, DATA_STORE_FILE_NAME).toOkioPath()
    val producePath = { path }

    return createDataStore(
        fileSystem = FileSystem.SYSTEM,
        producePath = producePath,
        optionsPreferencesSerializer = optionsPreferencesSerializer
    )
}