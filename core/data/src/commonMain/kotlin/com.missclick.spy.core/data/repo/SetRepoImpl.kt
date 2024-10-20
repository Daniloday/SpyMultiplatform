package com.missclick.spy.core.data.repo

import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.database.SetDataSource
import kotlinx.coroutines.flow.Flow
import com.missclick.spy.core.model.Set
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class SetRepoImpl(
    private val setDataSource: SetDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : SetRepo {
    override fun getSets(languageCode: String): Flow<List<String>> {
        return setDataSource.getSets(languageCode).flowOn(ioDispatcher)
    }

    override suspend fun getDefaultSet(languageCode: String): String {
        return withContext(ioDispatcher) {
            setDataSource.getDefaultSet(languageCode)
        }
    }

    override suspend fun getSet(setName: String, languageCode: String): Set {
        return withContext(ioDispatcher) {
            setDataSource.getSet(setName, languageCode)
        }
    }

    override suspend fun deleteSet(setName: String, languageCode: String) {
        withContext(ioDispatcher) {
            setDataSource.deleteSet(setName, languageCode)
        }
    }

    override suspend fun addSet(set: Set, languageCode: String) {
        withContext(ioDispatcher) {
            setDataSource.addSet(set, languageCode)
        }
    }

}