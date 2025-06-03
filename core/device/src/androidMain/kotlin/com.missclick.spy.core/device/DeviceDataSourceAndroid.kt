package com.missclick.spy.core.device

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale


internal class DeviceDataSourceAndroid(
    private val context: Context
) : DeviceDataSource {

    override suspend fun getCurrentLanguageCode(): String {
        return Locale.getDefault().language
    }

    override suspend fun setLanguage(languageCode: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(languageCode)
        } else {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        }
    }

}