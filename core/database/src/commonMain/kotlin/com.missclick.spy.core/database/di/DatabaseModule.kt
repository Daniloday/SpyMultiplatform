package com.missclick.spy.core.database.di

import androidx.room.RoomDatabaseConstructor
import com.missclick.spy.core.database.LanguageDataSource
import com.missclick.spy.core.database.SetDataSource
import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.database.dao.LanguageDao
import com.missclick.spy.core.database.dao.SetDao
import com.missclick.spy.core.database.dao.WordDao
import com.missclick.spy.core.database.datasource.LanguageDataSourceImpl
import com.missclick.spy.core.database.datasource.SetDataSourceImpl
import com.missclick.spy.core.database.datasource.WordDataSourceImpl
import com.missclick.spy.core.database.room.SpyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun platformModule(): Module

val databaseModule = module {
    single<WordDataSource> { WordDataSourceImpl(get(), get()) }
    single<SetDataSource> { SetDataSourceImpl(get(), get()) }
    single<LanguageDataSource> { LanguageDataSourceImpl(get()) }

    single<WordDao> { provideWordDao(get()) }
    single<SetDao> { provideSetDao(get()) }
    single<LanguageDao> { provideLanguageDao(get()) }
    includes(platformModule())
}

private fun provideWordDao(db: SpyDatabase) = db.wordDao()
private fun provideSetDao(db: SpyDatabase) = db.setDao()
private fun provideLanguageDao(db: SpyDatabase) = db.languageDao()

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<SpyDatabase> {
    override fun initialize(): SpyDatabase
}
