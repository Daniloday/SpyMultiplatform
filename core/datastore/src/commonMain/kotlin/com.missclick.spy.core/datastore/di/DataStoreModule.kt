package com.missclick.spy.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.missclick.spy.core.datastore.preferences.OptionsPreferences
import com.missclick.spy.core.datastore.preferences.OptionsPreferencesSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

expect val dataStoreModule : Module

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

internal const val DATA_STORE_FILE_NAME = "proto_datastore.preferences_pb"


