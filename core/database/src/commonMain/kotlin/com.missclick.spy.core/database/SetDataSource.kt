package com.missclick.spy.core.database

import com.missclick.spy.core.model.Set
import kotlinx.coroutines.flow.Flow

interface SetDataSource {
    fun getSets(languageCode: String): Flow<List<Set>>
    suspend fun getSet(setKey: String, languageCode: String): Set
    suspend fun getDefaultSet(languageCode: String): String
    suspend fun addSet(set: Set, languageCode: String)
    suspend fun deleteSet(setKey: String, languageCode: String)
}