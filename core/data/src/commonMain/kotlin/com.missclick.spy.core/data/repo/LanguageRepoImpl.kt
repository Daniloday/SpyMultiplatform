package com.missclick.spy.core.data.repo

import com.missclick.spy.core.data.LanguageRepo
import com.missclick.spy.core.database.LanguageDataSource
import com.missclick.spy.core.device.DeviceDataSource
import com.missclick.spy.core.model.Language
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class LanguageRepoImpl(
    private val languageDataSource: LanguageDataSource,
    private val deviceDataSource: DeviceDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : LanguageRepo {

    override suspend fun checkIsExistLanguage(languageCode: String): Boolean =
        withContext(ioDispatcher) { languageDataSource.checkIsExistLanguage(languageCode) }

    override suspend fun getLanguages(): List<Language> =
        withContext(ioDispatcher) { languageDataSource.getLanguages() }

    override suspend fun getCurrentLanguageCode(): String =
        withContext(ioDispatcher) { deviceDataSource.getCurrentLanguageCode() }

    override suspend fun setLanguage(languageCode: String) {
        withContext(ioDispatcher) { deviceDataSource.setLanguage(languageCode) }
    }

    override suspend fun getDefaultLanguage(): String = withContext(ioDispatcher) {
        val device = deviceDataSource.getCurrentLanguageCode()
        if (languageDataSource.checkIsExistLanguage(device)) device else "en"
    }

    override suspend fun getSetLanguage(setKey: String): String = withContext(ioDispatcher) {
        languageDataSource.getSetLanguageByKey(setKey) ?: "en"
    }
}