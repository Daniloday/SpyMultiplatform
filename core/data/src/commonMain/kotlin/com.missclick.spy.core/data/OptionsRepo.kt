package com.missclick.spy.core.data

import com.missclick.spy.core.model.Options
import kotlinx.coroutines.flow.Flow

interface OptionsRepo {

    val options: Flow<Options>

    suspend fun setPlayersCount(playersCount: Int)

    suspend fun setSpiesCount(spiesCount: Int)

    suspend fun setTime(time: Int)

    suspend fun setCollectionName(
        collectionName: String,
        languageCode: String,
    )

    suspend fun setLanguage(languageCode: String)

}