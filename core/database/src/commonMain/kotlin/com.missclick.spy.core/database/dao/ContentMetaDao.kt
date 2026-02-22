package com.missclick.spy.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.missclick.spy.core.database.enity.ContentMetaEntity

@Dao
internal interface ContentMetaDao {

    @Query("SELECT * FROM content_meta WHERE id = 1 LIMIT 1")
    suspend fun get(): ContentMetaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ContentMetaEntity)
}