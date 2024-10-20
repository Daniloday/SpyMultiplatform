package com.missclick.spy.core.database

import com.missclick.spy.core.model.Word
import kotlinx.coroutines.flow.Flow

interface WordDataSource {
    fun getWords(setName: String, languageCode: String): Flow<List<String>>
    suspend fun addWord(word: Word, setName: String, languageCode: String)
    suspend fun deleteWord(wordName: String)
    suspend fun preload()
}
