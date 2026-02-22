package com.missclick.spy.core.database.datasource

import com.missclick.spy.core.database.SetDataSource
import com.missclick.spy.core.database.dao.SetDao
import com.missclick.spy.core.database.enity.SetEntity
import com.missclick.spy.core.database.enity.asEntity
import com.missclick.spy.core.database.enity.asModel
import com.missclick.spy.core.model.Set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SetDataSourceImpl(
    private val setDao: SetDao,
) : SetDataSource {

    override fun getSets(languageCode: String): Flow<List<Set>> =
        setDao.getSets(languageCode).map { it.map(SetEntity::asModel) }

    override suspend fun getSetOrNull(setKey: String, languageCode: String): Set? =
        setDao.getSetByKey(setKey = setKey, languageCode = languageCode)?.asModel()

    override suspend fun getDefaultSetKey(languageCode: String): String =
        setDao.getDefaultSetKey(languageCode) ?: BASIC_SET_KEY

    override suspend fun addSet(set: Set, languageCode: String) {
        setDao.insertSet(set.asEntity(languageCode))
    }

    override suspend fun deleteSet(setKey: String, languageCode: String) {
        setDao.deleteSetByKey(setKey = setKey, languageCode = languageCode)
    }

    private companion object {
        const val BASIC_SET_KEY = "basic"
    }
}