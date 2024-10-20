package com.missclick.spy.core.database

import com.missclick.spy.core.model.Language

interface LanguageDataSource {
    suspend fun getLanguages(): List<Language>
    suspend fun getDefaultLanguage(): String
    suspend fun getSetLanguage(setName: String): String
    suspend fun checkIsExistLanguage(languageCode: String): Boolean
}