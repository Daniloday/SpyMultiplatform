package com.missclick.spy.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.missclick.spy.core.database.enity.LanguageEntity

@Dao
internal interface LanguageDao {

    @Query("""
       SELECT * FROM language WHERE code = :languageCode
    """)
    suspend fun getLanguage(
        languageCode: String,
    ): LanguageEntity

    @Query("""
       SELECT * FROM language
    """)
    suspend fun getLanguages(
    ): List<LanguageEntity>

    @Query("""
       SELECT DISTINCT code FROM language LIMIT 1
    """)
    suspend fun getDefaultLanguage(): String

    @Query("""
       SELECT EXISTS(SELECT 1 FROM language WHERE code = :languageCode LIMIT 1)
    """)
    suspend fun isExistLanguage(languageCode: String): Boolean

    @Query("""
       SELECT DISTINCT code FROM language
        INNER JOIN `set` ON `set`.language_id = language.id
        WHERE `set`.name = :setName LIMIT 1
    """)
    suspend fun getSetLanguage(setName: String): String
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(language: LanguageEntity)
}