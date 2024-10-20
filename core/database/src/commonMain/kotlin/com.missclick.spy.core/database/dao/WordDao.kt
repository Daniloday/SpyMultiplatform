package com.missclick.spy.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.missclick.spy.core.database.enity.LanguageEntity
import com.missclick.spy.core.database.enity.WordEntity
import com.missclick.spy.core.database.enity.SetEntity
import kotlinx.coroutines.flow.Flow


@Dao
internal interface WordDao {

    @Query("""
        SELECT word.name FROM word
        INNER JOIN `set` ON word.set_id = `set`.id
        INNER JOIN language ON `set`.language_id = language.id
        WHERE `set`.name = :setName AND code = :languageCode
    """)
    fun getWords(
        setName: String,
        languageCode: String
    ): Flow<List<String>>

    @Query("""
       DELETE FROM word WHERE name =:wordName
    """)
    suspend fun deleteWord(wordName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

}


