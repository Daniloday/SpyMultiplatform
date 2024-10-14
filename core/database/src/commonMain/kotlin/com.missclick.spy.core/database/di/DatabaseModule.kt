package com.missclick.spy.core.database.di

import androidx.room.RoomDatabaseConstructor
import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.database.WordDataSourceImpl
import com.missclick.spy.core.database.room.SpyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun platformModule(): Module

val databaseModule = module {
    single<WordDataSource> { WordDataSourceImpl(get()) }
    includes(platformModule())
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<SpyDatabase> {
    override fun initialize(): SpyDatabase
}
