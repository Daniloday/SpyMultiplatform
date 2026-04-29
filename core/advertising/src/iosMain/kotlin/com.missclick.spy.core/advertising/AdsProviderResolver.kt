package com.missclick.spy.core.advertising

import platform.CoreTelephony.CTCarrier
import platform.CoreTelephony.CTTelephonyNetworkInfo
import platform.Foundation.NSLocale
import platform.Foundation.NSLocaleCountryCode
import platform.Foundation.currentLocale

internal class AdsProviderResolver {

    private val networkInfo = CTTelephonyNetworkInfo()

    fun resolve(): AdsProvider {
        return if (resolveCountry() == RUSSIA_COUNTRY_CODE) {
            AdsProvider.APPLOVIN
        } else {
            AdsProvider.ADMOB
        }
    }

    private fun resolveCountry(): String? {
        return resolveCarrierCountry() ?: resolveLocaleCountry()
    }

    private fun resolveCarrierCountry(): String? {
        val providers = networkInfo.serviceSubscriberCellularProviders
        providers?.values
            ?.asSequence()
            ?.mapNotNull { provider ->
                (provider as? CTCarrier)?.isoCountryCode?.normalizedCountry()
            }
            ?.firstOrNull()
            ?.let { return it }

        return networkInfo.subscriberCellularProvider
            ?.isoCountryCode
            ?.normalizedCountry()
    }

    private fun resolveLocaleCountry(): String? {
        return (NSLocale.currentLocale
            .objectForKey(NSLocaleCountryCode) as? String
        )
            ?.normalizedCountry()
    }

    private fun String.normalizedCountry(): String? {
        return trim()
            .takeIf { it.isNotBlank() }
            ?.uppercase()
    }

    private companion object {
        const val RUSSIA_COUNTRY_CODE = "RU"
    }
}
