package com.missclick.spy.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.missclick.spy.core.common.di.SpyDispatchers
import com.missclick.spy.core.datastore.OptionsPreferencesSerializer
import com.missclick.spy.core.datastore.preferences.OptionsPreferences
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


internal actual fun platformModule(): Module = module {
    single { OptionsPreferencesSerializer() }
    single { provideDataStore(get(), ioDispatcher = get(named(SpyDispatchers.IO))) }
}

@OptIn(ExperimentalForeignApi::class)
private fun provideDataStore(
    optionsPreferencesSerializer: OptionsPreferencesSerializer,
    ioDispatcher: CoroutineDispatcher,
): DataStore<OptionsPreferences> {

    val producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/datastore/$DATA_STORE_FILE_NAME"
    }

    return DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            producePath = { producePath().toPath() },
            serializer = optionsPreferencesSerializer,
        ),
        scope = CoroutineScope(SupervisorJob() + ioDispatcher)
    )
}