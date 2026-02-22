package com.missclick.spy.core.datastore

import com.missclick.spy.core.model.OptionsStored
import kotlinx.coroutines.flow.Flow

interface OptionsDataSource {
    val options: Flow<OptionsStored>
    suspend fun setPlayersCount(playersCount: Int)
    suspend fun setSpiesCount(spiesCount: Int)
    suspend fun setTime(time: Int)
    suspend fun setSelectedSet(setKey: String)
    suspend fun setLanguage(languageCode: String)
    suspend fun changeHardMode(isHardModeEnabled: Boolean)
}