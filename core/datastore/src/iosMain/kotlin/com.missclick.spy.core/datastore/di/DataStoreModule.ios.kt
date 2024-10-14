package com.missclick.spy.core.datastore.di

import androidx.datastore.core.DataStore
import com.missclick.spy.core.datastore.OptionsDataSource
import com.missclick.spy.core.datastore.preferences.OptionsDataSourceImpl
import com.missclick.spy.core.datastore.preferences.OptionsPreferences
import com.missclick.spy.core.datastore.preferences.OptionsPreferencesSerializer
import kotlinx.cinterop.ExperimentalForeignApi
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


internal actual fun platformModule(): Module = module {
    single { provideDataStore(get()) }
}

@OptIn(ExperimentalForeignApi::class)
private fun provideDataStore(
    optionsPreferencesSerializer: OptionsPreferencesSerializer,
): DataStore<OptionsPreferences> {

    val producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$DATA_STORE_FILE_NAME"
    }

    return createDataStore(
        fileSystem = FileSystem.SYSTEM,
        producePath = { producePath().toPath() },
        optionsPreferencesSerializer = optionsPreferencesSerializer
    )
}