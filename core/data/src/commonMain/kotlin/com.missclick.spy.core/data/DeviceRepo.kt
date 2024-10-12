package com.missclick.spy.core.data

interface DeviceRepo {
    suspend fun getCurrentLanguageCode(): String
    suspend fun setLanguage(languageCode: String)
}