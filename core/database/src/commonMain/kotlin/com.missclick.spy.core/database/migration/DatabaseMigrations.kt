package com.missclick.spy.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL


internal object DatabaseMigrations {

    private fun addNewSets(newSetsLoader: NewSetsLoader, connection: SQLiteConnection, fileName: String) {
        val languages = newSetsLoader.getNewSets(fileName)
        languages.forEach { language ->
            language.sets.forEach { set ->
                connection.execSQL("INSERT INTO `set` (name, language_id, is_custom, is_premium, is_pro) VALUES ('${set.name}', ${language.languageId}, 0, ${if (set.isPremium) 1 else 0}, ${if (set.isPro) 1 else 0})")
                val setId = connection.prepare("SELECT last_insert_rowid()").use { stmt ->
                    stmt.step()
                    stmt.getLong(0)
                }
                set.words.forEach { word ->
                    connection.execSQL("INSERT INTO `word` (name, set_id, is_hidden) VALUES ('${word}', $setId, 0)")
                }
            }
        }
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(connection: SQLiteConnection) {
            connection.execSQL("ALTER TABLE `set` ADD COLUMN is_premium INTEGER NOT NULL DEFAULT 0")
        }
    }


    fun getMigration23(newSetsLoader: NewSetsLoader) = object : Migration(2, 3) {
        override fun migrate(connection: SQLiteConnection) {

            val languages = newSetsLoader.getNewSets("v3.json")

            languages.forEach { language ->
                language.sets.forEach { set ->
                    connection.execSQL("INSERT INTO `set` (name, language_id, is_custom, is_premium) VALUES ('${set.name}', ${language.languageId}, 0, ${if (set.isPremium) 1 else 0})")
                    val setId = connection.prepare("SELECT last_insert_rowid()").use { stmt ->
                        stmt.step()
                        stmt.getLong(0)
                    }
                    set.words.forEach { word ->
                        connection.execSQL("INSERT INTO `word` (name, set_id, is_hidden) VALUES ('${word}', $setId, 0)")
                    }
                }
            }
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(connection: SQLiteConnection) {
            connection.execSQL("ALTER TABLE `set` ADD COLUMN is_pro INTEGER NOT NULL DEFAULT 0")
        }
    }

    fun getMigration45(newSetsLoader: NewSetsLoader) = object : Migration(4, 5) {
        override fun migrate(connection: SQLiteConnection) {
            addNewSets(newSetsLoader, connection, "v5.json")
        }
    }

    fun getMigration56(newSetsLoader: NewSetsLoader) = object : Migration(5, 6) {
        override fun migrate(connection: SQLiteConnection) {
            addNewSets(newSetsLoader, connection, "v6.json")
        }
    }

}