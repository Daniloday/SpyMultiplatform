package com.missclick.spy.core.datastore.preferences

import androidx.datastore.core.DataStore
import com.missclick.spy.core.datastore.OptionsDataSource
import kotlinx.coroutines.flow.map



internal class OptionsDataSourceImpl(
    private val optionsPreferences: DataStore<OptionsPreferences>
) : OptionsDataSource {

    override val options = optionsPreferences.data.map { it.asModel() }

    override suspend fun setPlayersCount(playersCount: Int) {
        optionsPreferences.updateData { it.copy(playersCount = playersCount) }
    }

    override suspend fun setSpiesCount(spiesCount: Int) {
        optionsPreferences.updateData { it.copy(spiesCount = spiesCount) }
    }

    override suspend fun setTime(time: Int) {
        optionsPreferences.updateData { it.copy(time = time) }
    }

    override suspend fun setSelectedSet(
        setKey: String,
    ) {
        optionsPreferences.updateData { current ->
            current.copy(
                selectedSetKey = setKey,
            )
        }
    }

    override suspend fun setLanguage(languageCode: String) {
        optionsPreferences.updateData { it.copy(selectedLanguageCode = languageCode) }
    }

    override suspend fun changeHardMode(isHardModeEnabled: Boolean) {
        optionsPreferences.updateData { it.copy(isHardModeEnabled = isHardModeEnabled) }
    }
}