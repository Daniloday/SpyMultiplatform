package com.missclick.spy.core.database

import com.missclick.spy.core.database.enity.LanguageEntity
import com.missclick.spy.core.database.enity.SetEntity
import com.missclick.spy.core.database.enity.WordEntity
import com.missclick.spy.core.database.enity.asEntity
import com.missclick.spy.core.database.enity.asModel
import com.missclick.spy.core.database.room.SpyDatabase
import com.missclick.spy.core.model.Set
import com.missclick.spy.core.model.Language
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class WordDataSourceImpl(
    private val db: SpyDatabase,
) : WordDataSource {

    override fun getCollections(languageCode: String): Flow<List<String>> {
        return db.wordDao().getCollections(languageCode)
    }

    override suspend fun getLanguages(): List<Language> {
        println(db.wordDao().getLanguages())
        return db.wordDao().getLanguages().map { it.asModel() }
    }

    override suspend fun getCollection(collectionName: String, languageCode: String): Set {
        return db.wordDao().getCollection(collectionName, languageCode).asModel()
    }

    override suspend fun getDefaultCollection(languageCode: String): String {
        return db.wordDao().getDefaultCollection(languageCode)
    }

    override fun getWords(collection: String, languageCode: String): Flow<List<String>> {
        return db.wordDao().getWords(collectionName = collection, languageCode)
    }

    override suspend fun deleteCollection(collectionName: String, languageCode: String) {
        db.wordDao().deleteCollection(collectionName, languageCode)
    }

    override suspend fun deleteWord(wordName: String) {
        db.wordDao().deleteWord(wordName)
    }

    override suspend fun addWord(
        word: Word,
        collectionName: String,
        languageCode: String,
    ) {
        val collectionEntity = db.wordDao().getCollection(collectionName, languageCode)
        val wordEntity = word.asEntity(collectionEntity.id)
        db.wordDao().insertWord(wordEntity)
    }

    override suspend fun addCollection(set: Set, languageCode: String) {
        val languageEntity = db.wordDao().getLanguage(languageCode)
        val collectionEntity = set.asEntity(languageEntity.id)
        db.wordDao().insertCollection(collectionEntity)
    }

    override suspend fun checkIsExistLanguage(languageCode: String): Boolean {
        return db.wordDao().isExistLanguage(languageCode)
    }

    override suspend fun getDefaultLanguage(): String {
        return db.wordDao().getDefaultLanguage()
    }

    override suspend fun getCollectionLanguage(collectionName: String): String {
        return db.wordDao().getCollectionLanguage(collectionName)
    }

    override suspend fun preload() {
        db.wordDao().insertLanguage(LanguageEntity(code = "en", name = "Eng"))
        db.wordDao().insertCollection(SetEntity(name = "set1", languageId = 1))
        db.wordDao().insertWord(WordEntity(name = "word2", setId = 1))
        db.wordDao().insertWord(WordEntity(name = "word3", setId = 1))
    }

}