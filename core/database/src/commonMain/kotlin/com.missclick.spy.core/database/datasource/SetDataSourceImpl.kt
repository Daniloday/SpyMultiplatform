package com.missclick.spy.core.database.datasource

import com.missclick.spy.core.database.SetDataSource
import com.missclick.spy.core.database.dao.LanguageDao
import com.missclick.spy.core.database.dao.SetDao
import com.missclick.spy.core.database.enity.asEntity
import com.missclick.spy.core.database.enity.asModel
import com.missclick.spy.core.database.room.SpyDatabase
import kotlinx.coroutines.flow.Flow
import com.missclick.spy.core.model.Set
import kotlinx.coroutines.flow.map

internal class SetDataSourceImpl(
    private val setDao: SetDao,
    private val languageDao: LanguageDao,
) : SetDataSource {
    override fun getSets(languageCode: String): Flow<List<Set>> {
        return setDao.getSets(languageCode).map { setEntities ->
            setEntities.map { set ->
                set.asModel()
            }
        }
    }

    override suspend fun getSet(setName: String, languageCode: String): Set {
        return setDao.getSet(setName, languageCode).asModel()
    }

    override suspend fun getDefaultSet(languageCode: String): String {
        return setDao.getDefaultSet(languageCode)
    }

    override suspend fun addSet(set: Set, languageCode: String) {
        val languageEntity = languageDao.getLanguage(languageCode)
        val setEntity = set.asEntity(languageEntity.id)
        setDao.insertSet(setEntity)
    }

    override suspend fun deleteSet(setName: String, languageCode: String) {
        setDao.deleteSet(setName, languageCode)
    }

}