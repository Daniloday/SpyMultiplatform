package com.missclick.spy.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.missclick.spy.core.database.enity.SetEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface SetDao {

    @Query("""
       SELECT * FROM `set` 
        WHERE language_id IN (
           SELECT id FROM language WHERE code = :languageCode
       ) AND `set`.name = :setName
    """)
    suspend fun getSet(
        setName: String,
        languageCode: String,
    ): SetEntity

    @Query("""
       SELECT DISTINCT `set`.name FROM `set`
        INNER JOIN language ON `set`.language_id = language.id
        WHERE code = :languageCode
    """)
    fun getSets(
        languageCode: String,
    ): Flow<List<String>>

    @Query("""
       SELECT DISTINCT `set`.name FROM `set`
        INNER JOIN language ON `set`.language_id = language.id
        WHERE code = :languageCode LIMIT 1
    """)
    suspend fun getDefaultSet(
        languageCode: String,
    ): String
    
    @Query("""
       DELETE FROM `set`
       WHERE language_id IN (
           SELECT id FROM language WHERE code = :languageCode
       ) AND `set`.name = :setName
    """)
    suspend fun deleteSet(setName: String, languageCode: String)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: SetEntity)
}
