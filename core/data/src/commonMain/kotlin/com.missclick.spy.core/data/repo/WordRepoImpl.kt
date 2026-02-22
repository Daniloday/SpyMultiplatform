package com.missclick.spy.core.data.repo

import com.missclick.spy.core.data.LanguageRepo
import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.database.LanguageDataSource
import com.missclick.spy.core.database.SetDataSource
import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.device.DeviceDataSource
import com.missclick.spy.core.model.Set
import com.missclick.spy.core.model.Language
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class WordRepoImpl(
    private val wordDataSource: WordDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : WordRepo {

    override fun getWords(setKey: String, languageCode: String): Flow<List<String>> =
        wordDataSource.getWords(setKey, languageCode).flowOn(ioDispatcher)

    override suspend fun deleteWord(wordText: String, setKey: String, languageCode: String) {
        withContext(ioDispatcher) { wordDataSource.deleteWord(wordText, setKey, languageCode) }
    }

    override suspend fun addWord(word: Word, setKey: String, languageCode: String): Boolean =
        withContext(ioDispatcher) { wordDataSource.addWord(word, setKey, languageCode) }
}





