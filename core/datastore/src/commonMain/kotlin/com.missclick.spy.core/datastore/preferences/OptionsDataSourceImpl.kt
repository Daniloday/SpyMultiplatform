package com.missclick.spy.core.datastore.preferences

import androidx.datastore.core.DataStore
import com.missclick.spy.core.datastore.OptionsDataSource
import kotlinx.coroutines.flow.map

internal class OptionsDataSourceImpl(
    private val optionsPreferences: DataStore<OptionsPreferences>
): OptionsDataSource {

    override val options = optionsPreferences.data.map { it.asModel() }

    override suspend fun setPlayersCount(playersCount: Int) {
        optionsPreferences.updateData { currentData ->
            currentData.copy(
                playersCount = playersCount
            )
        }
    }

    override suspend fun setSpiesCount(spiesCount: Int) {
        optionsPreferences.updateData { currentData ->
            currentData.copy(
                spiesCount = spiesCount
            )
        }
    }

    override suspend fun setTime(time: Int) {
        optionsPreferences.updateData { currentData ->
            currentData.copy(
                time = time
            )
        }
    }

    override suspend fun setCollectionName(
        collectionName: String,
        languageCode: String,
        isCollectionPremium: Boolean,
    ) {
        optionsPreferences.updateData { currentData ->
            currentData.copy(
                collectionName = collectionName,
                collectionLanguageCode = languageCode,
                isSelectedCollectionPremium = isCollectionPremium
            )
        }
    }

    override suspend fun setLanguage(languageCode: String) {
        optionsPreferences.updateData { currentData ->
            currentData.copy(
                selectedLanguageCode = languageCode,
            )
        }
    }

    override suspend fun setPremiumStatus(isPremium: Boolean) {
        optionsPreferences.updateData { currentData ->
            currentData.copy(
                isPremium = isPremium,
            )
        }
    }

}