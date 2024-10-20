package com.missclick.spy.core.database.datasource

import com.missclick.spy.core.database.LanguageDataSource
import com.missclick.spy.core.database.dao.LanguageDao
import com.missclick.spy.core.database.enity.asModel
import com.missclick.spy.core.database.room.SpyDatabase
import com.missclick.spy.core.model.Language

internal class LanguageDataSourceImpl(
    private val languageDao: LanguageDao,
) : LanguageDataSource {
    override suspend fun getLanguages(): List<Language> {
        return languageDao.getLanguages().map { it.asModel() }
    }

    override suspend fun getDefaultLanguage(): String {
        return languageDao.getDefaultLanguage()
    }

    override suspend fun getSetLanguage(setName: String): String {
        return languageDao.getSetLanguage(setName)
    }

    override suspend fun checkIsExistLanguage(languageCode: String): Boolean {
        return languageDao.isExistLanguage(languageCode)
    }

}