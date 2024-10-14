package com.missclick.spy.core.device


internal class DeviceDataSourceJs: DeviceDataSource {

    override suspend fun getCurrentLanguageCode(): String {
        return "en"
    }

    override suspend fun setLanguage(languageCode: String) {

    }
}