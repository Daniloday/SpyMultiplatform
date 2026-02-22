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

    override fun getSets(languageCode: String): Flow<List<Set>> {
        return setDao.getSets(languageCode).map { entities ->
            entities.map(SetEntity::asModel)
        }
    }

    override suspend fun getSet(setKey: String, languageCode: String): Set {
        return requireNotNull(
            setDao.getSetByKey(setKey = setKey, languageCode = languageCode)
        ) { "Set not found: key=$setKey lang=$languageCode" }.asModel()
    }

    override suspend fun getDefaultSet(languageCode: String): String {
        return setDao.getDefaultSetKey(languageCode) ?: "basic"
    }

    override suspend fun addSet(set: Set, languageCode: String) {
        val entity = set.asEntity(languageCode)
        return setDao.insertSet(entity)
    }

    override suspend fun deleteSet(setKey: String, languageCode: String) {
        setDao.deleteSetByKey(setKey = setKey, languageCode = languageCode)
    }

}