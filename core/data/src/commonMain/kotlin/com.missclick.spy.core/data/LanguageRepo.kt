package com.missclick.spy.core.data

import com.missclick.spy.core.model.Language

interface LanguageRepo {
    suspend fun checkIsExistLanguage(languageCode: String): Boolean
    suspend fun getDefaultLanguage(): String
    suspend fun getSetLanguage(setName: String): String
    suspend fun getLanguages(): List<Language>
    suspend fun getCurrentLanguageCode(): String
    suspend fun setLanguage(languageCode: String)
}