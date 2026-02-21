package com.missclick.spy.core.datastore

import com.missclick.spy.core.model.Options
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface OptionsDataSource {

    val options: Flow<Options>

    suspend fun setPlayersCount(playersCount: Int)

    suspend fun setSpiesCount(spiesCount: Int)

    suspend fun setTime(time: Int)

    suspend fun setCollectionName(
        collectionName: String,
        languageCode: String,
        isCollectionPremium: Boolean,
    )

    suspend fun setLanguage(languageCode: String)

    suspend fun changeHardMode(isHardModeEnabled: Boolean)
}