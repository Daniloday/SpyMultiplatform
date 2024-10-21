package com.missclick.spy.core.device

import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.Foundation.setValue


internal class DeviceDataSourceIos() : DeviceDataSource {

    override suspend fun getCurrentLanguageCode(): String {
        return NSLocale.currentLocale.languageCode
    }

    override suspend fun setLanguage(languageCode: String) {
        val defaults = NSUserDefaults.standardUserDefaults
        defaults.setValue(
            value = listOf(languageCode),
            forKey = "AppleLanguages"
        )
    }

    override suspend fun requestRateUs() {

    }

}