package com.missclick.spy.core.data.repo

import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.database.WordDataSource
import com.missclick.spy.core.model.Set
import com.missclick.spy.core.model.Language
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.flow.Flow

internal class WordRepoImpl(
    private val wordDataSource: WordDataSource
) : WordRepo {

    override fun getWords(collection: String, languageCode: String): Flow<List<String>> {
        return wordDataSource.getWords(collection = collection, languageCode)
    }

    override fun getCollections(languageCode: String): Flow<List<String>> {
        return wordDataSource.getCollections(languageCode)
    }

    override suspend fun checkIsExistLanguage(languageCode: String): Boolean {
        return wordDataSource.checkIsExistLanguage(languageCode)
    }

    override suspend fun getDefaultLanguage(): String {
        return wordDataSource.getDefaultLanguage()
    }

    override suspend fun getCollectionLanguage(collectionName: String): String {
        return wordDataSource.getCollectionLanguage(collectionName)
    }

    override suspend fun getLanguages(): List<Language> {
        return wordDataSource.getLanguages()
    }

    override suspend fun getDefaultCollection(languageCode: String): String {
        return wordDataSource.getDefaultCollection(languageCode)
    }

    override suspend fun getCollection(collectionName: String, languageCode: String): Set {
        return wordDataSource.getCollection(collectionName, languageCode)
    }

    override suspend fun deleteCollection(name: String, languageCode: String) {
        wordDataSource.deleteCollection(name, languageCode)
    }

    override suspend fun deleteWord(name: String) {
        wordDataSource.deleteWord(name)
    }

    override suspend fun addWord(word: Word, collectionName: String, languageCode: String) {
        wordDataSource.addWord(word, collectionName, languageCode)
    }

    override suspend fun addCollection(set: Set, languageCode: String) {
        wordDataSource.addCollection(set, languageCode)
    }

    override suspend fun preload() {
        wordDataSource.preload()
    }

}