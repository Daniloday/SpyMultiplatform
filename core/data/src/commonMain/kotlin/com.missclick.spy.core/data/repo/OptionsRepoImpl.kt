package com.missclick.spy.core.data.repo

import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.datastore.OptionsDataSource
import com.missclick.spy.core.model.Options
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class OptionsRepoImpl(
    private val optionsDataSource: OptionsDataSource,
) : OptionsRepo {

    override val options: Flow<Options> = optionsDataSource.options

    override suspend fun setPlayersCount(playersCount: Int) {
        optionsDataSource.setPlayersCount(playersCount)
    }

    override suspend fun setSpiesCount(spiesCount: Int) {
        optionsDataSource.setSpiesCount(spiesCount)
    }

    override suspend fun setTime(time: Int) {
        optionsDataSource.setTime(time)
    }

    override suspend fun setCollectionName(
        collectionName: String,
        languageCode: String,
    ) {
        optionsDataSource.setCollectionName(
            collectionName = collectionName,
            languageCode = languageCode
        )
    }

    override suspend fun setLanguage(languageCode: String) {
        optionsDataSource.setLanguage(languageCode)
    }


}