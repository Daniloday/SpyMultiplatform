package com.missclick.spy.core.database.room


import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.missclick.spy.core.database.dao.LanguageDao
import com.missclick.spy.core.database.dao.SetDao
import com.missclick.spy.core.database.dao.WordDao
import com.missclick.spy.core.database.di.AppDatabaseConstructor
import com.missclick.spy.core.database.enity.LanguageEntity
import com.missclick.spy.core.database.enity.WordEntity
import com.missclick.spy.core.database.enity.SetEntity

internal const val DB_NAME = "spy-database"

@Database(
    entities = [
        WordEntity::class,
        SetEntity::class,
        LanguageEntity::class,
    ],
    version = 7,
    autoMigrations = [],
    exportSchema = true,
)
@ConstructedBy(AppDatabaseConstructor::class)
internal abstract class SpyDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun setDao(): SetDao
    abstract fun languageDao(): LanguageDao
}