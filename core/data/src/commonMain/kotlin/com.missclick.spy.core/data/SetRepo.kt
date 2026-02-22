package com.missclick.spy.core.data

import kotlinx.coroutines.flow.Flow
import com.missclick.spy.core.model.Set

interface SetRepo {
    
    fun getSets(languageCode: String): Flow<List<Set>>
    suspend fun getDefaultSet(languageCode: String): String
    suspend fun getSet(setKey: String, languageCode: String): Set
    suspend fun deleteSet(setKey: String, languageCode: String)
    suspend fun addSet(set: Set, languageCode: String)
    
}