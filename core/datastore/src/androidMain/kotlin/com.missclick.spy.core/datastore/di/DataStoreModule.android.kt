package com.missclick.spy.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.missclick.spy.core.common.di.SpyDispatchers
import com.missclick.spy.core.datastore.OptionsPreferencesSerializer
import com.missclick.spy.core.datastore.preferences.OptionsPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal actual fun platformModule(): Module = module {
    single { OptionsPreferencesSerializer() }
    single { provideDataStore(get(), get(), ioDispatcher = get(named(SpyDispatchers.IO))) }
}

private fun provideDataStore(
    context: Context,
    optionsPreferencesSerializer: OptionsPreferencesSerializer,
    ioDispatcher: CoroutineDispatcher,
): DataStore<OptionsPreferences> {

    return DataStoreFactory.create(
        serializer = optionsPreferencesSerializer,
        scope = CoroutineScope(ioDispatcher),
    ) {
        context.dataStoreFile(DATA_STORE_FILE_NAME)
    }
}
