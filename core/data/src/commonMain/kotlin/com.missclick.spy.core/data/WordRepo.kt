package com.missclick.spy.core.data

import com.missclick.spy.core.model.Set
import com.missclick.spy.core.model.Language
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepo {
    fun getWords(setKey: String, languageCode: String): Flow<List<String>>
    suspend fun deleteWord(wordText: String, setKey: String, languageCode: String)
    suspend fun addWord(word: Word, setKey: String, languageCode: String): Boolean
}



