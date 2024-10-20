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

    override suspend fun checkIsExistLanguage(languageCode: String): Boolean {
        return withContext(ioDispatcher) {
            languageDataSource.checkIsExistLanguage(languageCode)
        }
    }

    override suspend fun getDefaultLanguage(): String {
        return withContext(ioDispatcher) {
            languageDataSource.getDefaultLanguage()
        }
    }

    override suspend fun getSetLanguage(setName: String): String {
        return withContext(ioDispatcher) {
            languageDataSource.getSetLanguage(setName)
        }
    }

    override suspend fun getLanguages(): List<Language> {
        return withContext(ioDispatcher) {
            languageDataSource.getLanguages()
        }
    }

    override suspend fun getCurrentLanguageCode(): String {
        return withContext(ioDispatcher) {
            deviceDataSource.getCurrentLanguageCode()
        }
    }

    override suspend fun setLanguage(languageCode: String) {
        withContext(ioDispatcher) {
            deviceDataSource.setLanguage(languageCode)
        }
    }

}