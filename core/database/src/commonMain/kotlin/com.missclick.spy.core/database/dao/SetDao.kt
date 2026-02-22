package com.missclick.spy.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.missclick.spy.core.database.enity.SetEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface SetDao {

    @Query(
        """
        SELECT * FROM `set`
        WHERE language_code = :languageCode
          AND `key` = :setKey
        LIMIT 1
        """
    )
    suspend fun getSetByKey(
        setKey: String,
        languageCode: String,
    ): SetEntity?

    // ⚠️ Legacy: (languageCode + setName)
    @Query(
        """
        SELECT * FROM `set`
        WHERE language_code = :languageCode
          AND name = :setName
        LIMIT 1
        """
    )
    suspend fun getSetByName(
        setName: String,
        languageCode: String,
    ): SetEntity?

    @Query(
        """
        SELECT * FROM `set`
        WHERE language_code = :languageCode
        ORDER BY is_custom ASC, is_premium ASC, is_pro ASC, name COLLATE NOCASE ASC
        """
    )
    fun getSets(
        languageCode: String,
    ): Flow<List<SetEntity>>

    // Раньше возвращал name, теперь лучше вернуть SetEntity или key.
    // Оставляю "суть" (вернуть какое-то значение по умолчанию), но делаю безопасно.
    @Query(
        """
        SELECT name FROM `set`
        WHERE language_code = :languageCode
        ORDER BY is_custom ASC, name COLLATE NOCASE ASC
        LIMIT 1
        """
    )
    suspend fun getDefaultSetName(
        languageCode: String,
    ): String?

    // ✅ Правильнее для новой архитектуры: дефолтный setKey
    @Query(
        """
        SELECT `key` FROM `set`
        WHERE language_code = :languageCode
        ORDER BY is_custom ASC, `key` ASC
        LIMIT 1
        """
    )
    suspend fun getDefaultSetKey(
        languageCode: String,
    ): String?

    @Query(
        """
        DELETE FROM `set`
        WHERE language_code = :languageCode
          AND `key` = :setKey
        """
    )
    suspend fun deleteSetByKey(setKey: String, languageCode: String)

    @Query(
        """
        DELETE FROM `set`
        WHERE language_code = :languageCode
          AND name = :setName
        """
    )
    suspend fun deleteSetByName(setName: String, languageCode: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: SetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(sets: List<SetEntity>)
}