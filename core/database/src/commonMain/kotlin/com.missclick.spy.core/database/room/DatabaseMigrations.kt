package com.missclick.spy.core.database.room

import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.Update
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL


internal object DatabaseMigrations {



    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(connection: SQLiteConnection) {
            connection.execSQL("ALTER TABLE `set` ADD COLUMN is_premium INTEGER NOT NULL DEFAULT 0")
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(connection: SQLiteConnection) {
            connection.execSQL("INSERT INTO `set` (name, language_id, is_custom, is_premium) VALUES ('Test', 0, 0, 1)")
        }
    }

}