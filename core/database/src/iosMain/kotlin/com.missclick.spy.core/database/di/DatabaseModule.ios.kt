package com.missclick.spy.core.database.di

import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.database.WordDataSourceImpl
import com.missclick.spy.core.database.enity.LanguageEntity
import com.missclick.spy.core.database.enity.SetEntity
import com.missclick.spy.core.database.enity.WordEntity
import com.missclick.spy.core.database.room.DB_NAME
import com.missclick.spy.core.database.room.PRELOAD_DB_NAME
import com.missclick.spy.core.database.room.SpyDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSBundle
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSUserDomainMask

internal actual fun platformModule(): Module = module {
    single { getDatabaseBuilder() }
    single { provideDatabase(get()) }
}


private fun getDatabaseBuilder(): RoomDatabase.Builder<SpyDatabase> {
    val dbFilePath = documentDirectory() + "/" + DB_NAME
    return Room.databaseBuilder<SpyDatabase>(
        name = dbFilePath,
    )
}
private fun provideDatabase(
    builder: RoomDatabase.Builder<SpyDatabase>
): SpyDatabase {
    copyDatabaseIfNeeded()
    return builder
        .setDriver(BundledSQLiteDriver())
        .build()

}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

@OptIn(ExperimentalForeignApi::class)
fun copyDatabaseIfNeeded() {
    val fileManager = NSFileManager.defaultManager()

    // Путь к базе данных в папке ресурсов
    val bundlePath = NSBundle.mainBundle.pathForResource(name = "spy-database-preload", ofType = "db")
    val databasePath = documentDirectory() + "/" + DB_NAME

    // Если файл базы данных уже существует, пропускаем копирование
    if (!fileManager.fileExistsAtPath(databasePath)) {
        if (bundlePath != null) {
            fileManager.copyItemAtPath(bundlePath, toPath = databasePath, error = null)
        } else {
            throw IllegalStateException("Database not found in bundle")
        }
    }
}