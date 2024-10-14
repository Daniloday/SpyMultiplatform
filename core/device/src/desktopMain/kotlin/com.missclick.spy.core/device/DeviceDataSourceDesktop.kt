package com.missclick.spy.core.device


import java.util.Locale


internal class DeviceDataSourceDesktop : DeviceDataSource {

    override suspend fun getCurrentLanguageCode(): String {
        return Locale.getDefault().language
    }

    override suspend fun setLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
    }

}