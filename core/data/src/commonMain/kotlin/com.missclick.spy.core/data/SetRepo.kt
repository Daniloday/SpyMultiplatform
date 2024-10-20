package com.missclick.spy.core.data

import kotlinx.coroutines.flow.Flow
import com.missclick.spy.core.model.Set

interface SetRepo {
    
    fun getSets(languageCode: String): Flow<List<String>>
    suspend fun getDefaultSet(languageCode: String): String
    suspend fun getSet(setName: String, languageCode: String): Set
    suspend fun deleteSet(setName: String, languageCode: String)
    suspend fun addSet(set: Set, languageCode: String)
    
}