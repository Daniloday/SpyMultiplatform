package com.missclick.spy.core.database.datasource

import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.database.dao.SetDao
import com.missclick.spy.core.database.dao.WordDao
import com.missclick.spy.core.database.enity.WordEntity
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.flow.Flow

internal class WordDataSourceImpl(
    private val wordDao: WordDao,
    private val setDao: SetDao,
) : WordDataSource {

    override fun getWords(setKey: String, languageCode: String): Flow<List<String>> =
        wordDao.getWordsByKey(setKey = setKey, languageCode = languageCode)

    override suspend fun deleteWord(wordText: String, setKey: String, languageCode: String) {
        wordDao.deleteWordFromSet(wordText = wordText, setKey = setKey, languageCode = languageCode)
    }

    override suspend fun addWord(word: Word, setKey: String, languageCode: String): Boolean {
        val setEntity = setDao.getSetByKey(setKey = setKey, languageCode = languageCode) ?: return false

        wordDao.insertWord(
            WordEntity(
                id = 0,
                text = word.wordName,
                setId = setEntity.id,
            )
        )
        return true
    }
}