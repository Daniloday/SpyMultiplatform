package com.missclick.spy.core.database.content_loader

import com.missclick.spy.core.database.dao.ContentMetaDao
import com.missclick.spy.core.database.dao.LanguageDao
import com.missclick.spy.core.database.dao.SetDao
import com.missclick.spy.core.database.dao.WordDao
import com.missclick.spy.core.database.enity.ContentMetaEntity
import com.missclick.spy.core.database.enity.LanguageEntity
import com.missclick.spy.core.database.enity.SetEntity
import com.missclick.spy.core.database.enity.WordEntity
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

        val indexRaw = runCatching {
            loader.loadJson("spy-content-index.json")
        }.getOrNull() ?: return

        val index = runCatching {
            json.decodeFromString(SpyContentIndexDto.serializer(), indexRaw)
        }.getOrNull() ?: return

        val current = metaDao.get()?.contentVersion ?: 0
        if (index.contentVersion <= current) return

        // 1️⃣ Languages
        val languages = index.languages
            .mapNotNull {
                val code = it.code.trim()
                val name = it.name.trim()
                if (code.isBlank() || name.isBlank()) null
                else code to name
            }
            .distinctBy { it.first }

        languageDao.insertLanguages(
            languages.map { (code, name) ->
                LanguageEntity(code = code, name = name)
            }
        )

        val indexSets = index.sets
            .mapNotNull { if (it.key.isBlank()) null else it.key to it }
            .toMap()

        // 2️⃣ Per language
        for ((langCode, _) in languages) {

            val langRaw = runCatching {
                loader.loadJson("spy-content-$langCode.json")
            }.getOrNull() ?: continue

            val langDto = runCatching {
                json.decodeFromString(SpyContentLangDto.serializer(), langRaw)
            }.getOrNull() ?: continue

            if (langDto.language != langCode) continue

            val setNames = langDto.sets
                .mapNotNull {
                    val key = it.key.trim()
                    val name = it.name.trim()
                    if (key.isBlank() || name.isBlank()) null
                    else key to name
                }
                .toMap()

            // 2a️⃣ Sets
            val setsToInsert = indexSets.values
                .asSequence()
                .filter { it.onlyLanguages.isNullOrEmpty() || it.onlyLanguages.contains(langCode) }
                .mapNotNull { indexSet ->
                    val name = setNames[indexSet.key] ?: return@mapNotNull null

                    SetEntity(
                        id = 0,
                        key = indexSet.key,
                        name = name,
                        languageCode = langCode,
                        isCustom = false,
                        isPremium = indexSet.flags?.premium ?: false,
                        isPro = indexSet.flags?.pro ?: false,
                    )
                }
                .toList()

            setDao.insertSets(setsToInsert)

            // 2b️⃣ Words
            val wordsBySet = langDto.words
                .mapNotNull {
                    val key = it.setKey.trim()
                    if (key.isBlank()) null
                    else key to it.items
                }
                .groupBy({ it.first }, { it.second })

            for ((setKey, blocks) in wordsBySet) {

                val indexSet = indexSets[setKey] ?: continue
                if (!indexSet.onlyLanguages.isNullOrEmpty()
                    && !indexSet.onlyLanguages.contains(langCode)
                ) continue

                val set = setDao.getSetByKey(setKey, langCode) ?: continue

                wordDao.deleteDefaultWordsBySetId(set.id)

                val words = blocks
                    .flatten()
                    .asSequence()
                    .map { it.trim() }
                    .filter { it.isNotBlank() }
                    .distinct()
                    .toList()

                if (words.isEmpty()) continue

                wordDao.insertWords(
                    words.map {
                        WordEntity(
                            id = 0,
                            text = it,
                            setId = set.id,
                        )
                    }
                )
            }
        }

        metaDao.upsert(
            ContentMetaEntity(
                schema = index.schema,
                contentVersion = index.contentVersion,
                generatedAt = index.generatedAt,
                updatedAtEpochMs = kotlin.time.Clock.System.now().toEpochMilliseconds(),
            )
        )
    }
}