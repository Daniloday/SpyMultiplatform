package com.missclick.spy.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.database.WordDataSourceImpl
import com.missclick.spy.core.database.room.DB_NAME
import com.missclick.spy.core.database.room.PRELOAD_DB_NAME
import com.missclick.spy.core.database.room.SpyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single { getDatabaseBuilder(get()) }
    single { provideDatabase(get()) }
}

private fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<SpyDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(DB_NAME)
    return Room.databaseBuilder<SpyDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

private fun provideDatabase(
    builder: RoomDatabase.Builder<SpyDatabase>
): SpyDatabase {
    return builder
        .createFromAsset(PRELOAD_DB_NAME)
        .build()
}

