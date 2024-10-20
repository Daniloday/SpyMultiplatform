package com.missclick.spy.core.data

import com.missclick.spy.core.model.Set
import com.missclick.spy.core.model.Language
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepo {

    fun getWords(setName: String, languageCode: String): Flow<List<String>>
    suspend fun addWord(word: Word, setName: String, languageCode: String)
    suspend fun deleteWord(wordName: String)

}



