package com.missclick.spy.core.device

interface DeviceDataSource {
    suspend fun getCurrentLanguageCode(): String
    suspend fun setLanguage(languageCode: String)
    suspend fun requestRateUs()
}