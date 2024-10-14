package com.missclick.spy.core.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.database.WordDataSourceImpl
import com.missclick.spy.core.database.room.SpyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

internal actual fun platformModule(): Module = module {
    single { getDatabaseBuilder() }
    single { provideDatabase(get()) }
}

private fun getDatabaseBuilder(): RoomDatabase.Builder<SpyDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "my_room.db")
    return Room.databaseBuilder<SpyDatabase>(
        name = dbFile.absolutePath,
    )
}

private fun provideDatabase(
    builder: RoomDatabase.Builder<SpyDatabase>
): SpyDatabase {
    return builder
        //.createFromAsset("spy-database-preload.db")
        .build()
}
