package com.missclick.spy.core.database.room


import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.missclick.spy.core.database.di.AppDatabaseConstructor
import com.missclick.spy.core.database.enity.LanguageEntity
import com.missclick.spy.core.database.enity.WordEntity
import com.missclick.spy.core.database.enity.SetEntity


@Database(
    entities = [
        WordEntity::class,
        SetEntity::class,
        LanguageEntity::class,
    ],
    version = 1,
    autoMigrations = [

    ],
    exportSchema = true,
)
@ConstructedBy(AppDatabaseConstructor::class)
internal abstract class SpyDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}