package com.missclick.spy.core.database.di

import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.database.WordDataSourceImpl
import com.missclick.spy.core.database.room.SpyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

expect val databaseModule: Module

//internal fun provideDatabase(
//    builder: RoomDatabase.Builder<SpyDatabase>
//): SpyDatabase {
//    return builder
//        .setDriver(BundledSQLiteDriver())
//        .build()
//}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<SpyDatabase> {
    override fun initialize(): SpyDatabase
}
