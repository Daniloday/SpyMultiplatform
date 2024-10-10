package com.missclick.spy.core.data

import com.missclick.spy.core.model.Set
import com.missclick.spy.core.model.Language
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepo {

    fun getWords(collection: String, languageCode: String): Flow<List<String>>
    fun getCollections(languageCode: String): Flow<List<String>>

    suspend fun checkIsExistLanguage(languageCode: String): Boolean
    suspend fun getDefaultLanguage(): String
    suspend fun getCollectionLanguage(collectionName: String): String
    suspend fun getLanguages(): List<Language>

    suspend fun getDefaultCollection(languageCode: String): String
    suspend fun getCollection(collectionName: String, languageCode: String): Set

    suspend fun deleteCollection(name: String, languageCode: String)
    suspend fun deleteWord(name: String)

    suspend fun addWord(word: Word, collectionName: String, languageCode: String)
    suspend fun addCollection(set: Set, languageCode: String)
    suspend fun preload()

}