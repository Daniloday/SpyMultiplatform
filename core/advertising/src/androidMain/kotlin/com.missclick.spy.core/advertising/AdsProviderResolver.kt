package com.missclick.spy.core.advertising

import android.content.Context
import android.telephony.TelephonyManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.Locale

const val MIN_FETCH_INTERVAL = 7200L

enum class AdsProvider {
    ADMOB,
    APPLOVIN
}

class AdsProviderResolver(
    private val context: Context,
    private val remoteConfig: FirebaseRemoteConfig,
) {

    companion object {
        private const val KEY = "ads_config"
    }

    fun resolve(): AdsProvider {
        remoteConfig.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(MIN_FETCH_INTERVAL)
                .build()
        )
        remoteConfig.fetchAndActivate()
        return when (remoteConfig.getString(KEY).lowercase(Locale.US)) {
            "admob" -> AdsProvider.ADMOB
            "applovin" -> AdsProvider.APPLOVIN
            else -> fallbackByCountry()
        }
    }

    private fun fallbackByCountry(): AdsProvider {
        val country = resolveCountry()
        return if (country == "RU") AdsProvider.APPLOVIN else AdsProvider.ADMOB
    }

    private fun resolveCountry(): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager

        tm?.networkCountryIso
            ?.takeIf { it.isNotBlank() }
            ?.let { return it.uppercase(Locale.US) }

        tm?.simCountryIso
            ?.takeIf { it.isNotBlank() }
            ?.let { return it.uppercase(Locale.US) }

        return Locale.getDefault().country
            .takeIf { it.isNotBlank() }
            ?.uppercase(Locale.US)
    }
}
