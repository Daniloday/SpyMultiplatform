package com.missclick.spy.core.data.repo

import com.missclick.spy.core.common.di.SpyDispatchers
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.datastore.OptionsDataSource
import com.missclick.spy.core.model.Options
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class OptionsRepoImpl(
    private val optionsDataSource: OptionsDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : OptionsRepo {

    override val options: Flow<Options> = optionsDataSource.options.flowOn(ioDispatcher)

    override suspend fun setPlayersCount(playersCount: Int) {
        withContext(ioDispatcher) {
            optionsDataSource.setPlayersCount(playersCount)
        }
    }

    override suspend fun setSpiesCount(spiesCount: Int) {
        withContext(ioDispatcher) {
            optionsDataSource.setSpiesCount(spiesCount)
        }
    }

    override suspend fun setTime(time: Int) {
        withContext(ioDispatcher) {
            optionsDataSource.setTime(time)
        }
    }

    override suspend fun setCollectionName(
        collectionName: String,
        languageCode: String,
    ) {
        withContext(ioDispatcher) {
            optionsDataSource.setCollectionName(
                collectionName = collectionName,
                languageCode = languageCode
            )
        }
    }

    override suspend fun setLanguage(languageCode: String) {
        withContext(ioDispatcher) {
            optionsDataSource.setLanguage(languageCode)
        }
    }


}