package com.missclick.spy.core.device

import android.app.Activity
import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import java.util.Locale


internal class DeviceDataSourceAndroid(
    private val activity: Lazy<Activity>
) : DeviceDataSource {

    override suspend fun getCurrentLanguageCode(): String {
        return Locale.getDefault().language
    }

    override suspend fun setLanguage(languageCode: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.value.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(languageCode)
        } else {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val configuration = activity.value.resources.configuration
            configuration.setLocale(locale)
            activity.value.resources.updateConfiguration(configuration, activity.value.resources.displayMetrics)
        }
    }

}