package com.missclick.spy.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.missclick.spy.core.database.NewSetsLoaderAndroid
import com.missclick.spy.core.database.room.DB_NAME
import com.missclick.spy.core.database.migration.NewSetsLoader
import com.missclick.spy.core.database.room.PRELOAD_DB_NAME
import com.missclick.spy.core.database.room.SpyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single { provideDatabaseBuilder(get()) }
    single<NewSetsLoader> { NewSetsLoaderAndroid(get()) }
}

private fun provideDatabaseBuilder(ctx: Context): RoomDatabase.Builder<SpyDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(DB_NAME)
    return Room.databaseBuilder<SpyDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
        .createFromAsset(PRELOAD_DB_NAME)
}



