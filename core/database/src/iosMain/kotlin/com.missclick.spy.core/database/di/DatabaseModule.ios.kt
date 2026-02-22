package com.missclick.spy.core.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.missclick.spy.core.database.room.DB_NAME
import com.missclick.spy.core.database.room.SpyDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSBundle
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal actual fun platformModule(): Module = module {
    single { provideDatabaseBuilder() }
}


private fun provideDatabaseBuilder(): RoomDatabase.Builder<SpyDatabase> {
    val dbFilePath = documentDirectory() + "/" + DB_NAME
    return Room.databaseBuilder<SpyDatabase>(
        name = dbFilePath,
    )
        .setDriver(BundledSQLiteDriver())
}

@OptIn(ExperimentalForeignApi::class)
fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}