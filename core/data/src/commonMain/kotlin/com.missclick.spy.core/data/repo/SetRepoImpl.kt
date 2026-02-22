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

    override fun getSets(languageCode: String): Flow<List<Set>> =
        setDataSource.getSets(languageCode).flowOn(ioDispatcher)

    override suspend fun getDefaultSet(languageCode: String): String =
        withContext(ioDispatcher) { setDataSource.getDefaultSetKey(languageCode) }

    override suspend fun getSetOrNull(setKey: String, languageCode: String): Set? =
        withContext(ioDispatcher) { setDataSource.getSetOrNull(setKey, languageCode) }

    override suspend fun deleteSet(setKey: String, languageCode: String) {
        withContext(ioDispatcher) { setDataSource.deleteSet(setKey, languageCode) }
    }

    override suspend fun addSet(set: Set, languageCode: String) {
        withContext(ioDispatcher) { setDataSource.addSet(set, languageCode) }
    }
}