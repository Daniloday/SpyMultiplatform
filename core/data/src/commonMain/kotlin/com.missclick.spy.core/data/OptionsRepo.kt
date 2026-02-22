package com.missclick.spy.core.data

import com.missclick.spy.core.model.OptionsStored
import kotlinx.coroutines.flow.Flow

interface OptionsRepo {

    val options: Flow<OptionsStored>

    suspend fun setPlayersCount(playersCount: Int)

    suspend fun setSpiesCount(spiesCount: Int)

    suspend fun setTime(time: Int)

    suspend fun setSelectedSet(setKey: String)

    suspend fun setLanguage(languageCode: String)

    suspend fun changeHardMode(isHardModeEnabled: Boolean)

}