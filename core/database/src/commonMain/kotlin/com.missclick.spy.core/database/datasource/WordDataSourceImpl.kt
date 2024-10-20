package com.missclick.spy.core.database.datasource

import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.database.dao.SetDao
import com.missclick.spy.core.database.dao.WordDao
import com.missclick.spy.core.database.enity.LanguageEntity
import com.missclick.spy.core.database.enity.SetEntity
import com.missclick.spy.core.database.enity.WordEntity
import com.missclick.spy.core.database.enity.asEntity
import com.missclick.spy.core.database.room.SpyDatabase
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.flow.Flow

internal class WordDataSourceImpl(
    private val wordDao: WordDao,
    private val setDao: SetDao
) : WordDataSource {

    override fun getWords(setName: String, languageCode: String): Flow<List<String>> {
        return wordDao.getWords(setName = setName, languageCode)
    }

    override suspend fun deleteWord(wordName: String) {
        wordDao.deleteWord(wordName)
    }

    override suspend fun addWord(
        word: Word,
        setName: String,
        languageCode: String,
    ) {
        val collectionEntity = setDao.getSet(setName, languageCode)
        val wordEntity = word.asEntity(collectionEntity.id)
        wordDao.insertWord(wordEntity)
    }


    override suspend fun preload() {
//        wordDao.insertLanguage(LanguageEntity(code = "en", name = "Eng"))
//        wordDao.insertSet(SetEntity(name = "set1", languageId = 1))
//        wordDao.insertWord(WordEntity(name = "word2", setId = 1))
//        wordDao.insertWord(WordEntity(name = "word3", setId = 1))
    }

}





