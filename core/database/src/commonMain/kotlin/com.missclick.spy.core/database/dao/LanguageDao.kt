package com.missclick.spy.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.missclick.spy.core.database.enity.LanguageEntity

@Dao
internal interface LanguageDao {

    @Query("SELECT * FROM language WHERE code = :languageCode LIMIT 1")
    suspend fun getLanguage(languageCode: String): LanguageEntity?

    @Query("SELECT * FROM language ORDER BY code")
    suspend fun getLanguages(): List<LanguageEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM language WHERE code = :languageCode)")
    suspend fun isExistLanguage(languageCode: String): Boolean

    @Query(
        """
        SELECT s.language_code
        FROM `set` s
        WHERE s.`key` = :setKey
        LIMIT 1
        """
    )
    suspend fun getSetLanguageByKey(setKey: String): String?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(language: LanguageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguages(languages: List<LanguageEntity>)
}