package com.missclick.spy.core.database

import com.missclick.spy.core.model.Word
import kotlinx.coroutines.flow.Flow

interface WordDataSource {
    fun getWords(setKey: String, languageCode: String): Flow<List<String>>
    suspend fun deleteWord(wordText: String, setKey: String, languageCode: String)
    suspend fun addWord(word: Word, setKey: String, languageCode: String): Boolean
}
