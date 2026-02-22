package com.missclick.spy.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.missclick.spy.core.database.enity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface WordDao {

    // ✅ Правильно: слова по (languageCode + setKey)
    @Query(
        """
        SELECT w.text
        FROM word w
        INNER JOIN `set` s ON w.set_id = s.id
        WHERE s.`key` = :setKey
          AND s.language_code = :languageCode
        ORDER BY w.text COLLATE NOCASE ASC
        """
    )
    fun getWordsByKey(
        setKey: String,
        languageCode: String
    ): Flow<List<String>>

    // ⚠️ Legacy: слова по (languageCode + setName)
    @Query(
        """
        SELECT w.text
        FROM word w
        INNER JOIN `set` s ON w.set_id = s.id
        WHERE s.name = :setName
          AND s.language_code = :languageCode
        ORDER BY w.text COLLATE NOCASE ASC
        """
    )
    fun getWordsByName(
        setName: String,
        languageCode: String
    ): Flow<List<String>>

    // ✅ Нормально: удалить слово внутри конкретного сета
    @Query(
        """
        DELETE FROM word
        WHERE set_id = (
            SELECT id FROM `set`
            WHERE language_code = :languageCode
              AND `key` = :setKey
            LIMIT 1
        )
          AND text = :wordText
        """
    )
    suspend fun deleteWordFromSet(
        wordText: String,
        setKey: String,
        languageCode: String
    )

    // ⚠️ Legacy: как у тебя было (удаляет везде)
    @Query("DELETE FROM word WHERE text = :wordText")
    suspend fun deleteWordEverywhere(wordText: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordEntity>)
}