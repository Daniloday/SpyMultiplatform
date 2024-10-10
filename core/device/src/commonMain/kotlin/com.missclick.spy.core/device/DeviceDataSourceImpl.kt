package com.missclick.spy.core.device


internal expect val languageCode: String

internal class DeviceDataSourceImpl : DeviceDataSource {

    override suspend fun getCurrentLanguageCode(): String {
        return languageCode
    }

}