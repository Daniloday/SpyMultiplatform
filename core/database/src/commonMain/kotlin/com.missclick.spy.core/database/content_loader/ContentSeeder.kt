package com.missclick.spy.core.database.content_loader

import com.missclick.spy.core.database.dao.ContentMetaDao
import com.missclick.spy.core.database.dao.LanguageDao
import com.missclick.spy.core.database.dao.SetDao
import com.missclick.spy.core.database.dao.WordDao
import com.missclick.spy.core.database.enity.ContentMetaEntity
import com.missclick.spy.core.database.enity.LanguageEntity
import com.missclick.spy.core.database.enity.SetEntity
import com.missclick.spy.core.database.enity.WordEntity
import com.missclick.spy.core.database.room.SpyDatabase
import kotlinx.serialization.json.Json

internal class ContentSeeder(
    private val metaDao: ContentMetaDao,
    private val languageDao: LanguageDao,
    private val setDao: SetDao,
    private val wordDao: WordDao,
    private val loader: ContentJsonLoader,
) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    suspend fun syncIfNeeded() {
        val raw = loader.loadSpyContentJson()
        val content = json.decodeFromString(SpyContentDto.serializer(), raw)

        val current = metaDao.get()?.contentVersion ?: 0
        if (content.contentVersion <= current) return


        // 1) Languages
        languageDao.insertLanguages(
            content.languages
                .filter { it.code.isNotBlank() }
                .map { LanguageEntity(code = it.code, name = it.name) }
        )

        // 2) Sets (only default, not user custom)
        val defaultSets = content.sets
            .filter { it.key.isNotBlank() && it.language.isNotBlank() }
            .map { dto ->
                SetEntity(
                    id = 0,
                    key = dto.key,
                    name = dto.name,
                    languageCode = dto.language,
                    isCustom = false,
                    isPremium = dto.flags?.premium ?: false,
                    isPro = dto.flags?.pro ?: false,
                )
            }

        setDao.insertSets(defaultSets)

        // 3) Words: replace only default words
        val wordsGrouped = content.words
            .filter { it.setKey.isNotBlank() && it.language.isNotBlank() }
            .groupBy { it.language to it.setKey }

        for ((langAndKey, blocks) in wordsGrouped) {
            val (lang, setKey) = langAndKey
            val set = setDao.getSetByKey(setKey = setKey, languageCode = lang) ?: continue

            wordDao.deleteDefaultWordsBySetId(set.id)

            val items = blocks
                .flatMap { it.items }
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .distinct()

            wordDao.insertWords(
                items.map { text ->
                    WordEntity(
                        id = 0,
                        text = text,
                        setId = set.id,
                    )
                }
            )
        }

        metaDao.upsert(
            ContentMetaEntity(
                schema = content.schema,
                contentVersion = content.contentVersion,
                generatedAt = content.generatedAt,
                updatedAtEpochMs = kotlin.time.Clock.System.now().toEpochMilliseconds(),
            )
        )

    }
}