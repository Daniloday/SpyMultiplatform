package com.missclick.spy.core.database.migration

import com.missclick.spy.core.model.Language
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

abstract class NewSetsLoader {
    protected abstract fun load(fileName: String): String
    fun getNewSets(fileName: String): List<LanguageRaw> {
        val jsonContent = load(fileName)
        val parsed = Json.decodeFromString<LanguageData>(jsonContent)
        return parsed.languages
    }
}



@Serializable
private data class LanguageData(
    val languages: List<LanguageRaw>
)

