package com.missclick.spy.core.database.content_loader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SpyContentIndexDto(
    val schema: String,
    val contentVersion: Int,
    val generatedAt: String? = null,
    val languages: List<LanguageDto>,
    val sets: List<SetIndexDto>,
)

@Serializable
internal data class LanguageDto(
    @SerialName("key") val code: String,
    val name: String,
)

@Serializable
internal data class SetIndexDto(
    val key: String,
    val flags: FlagsDto? = null,
    val onlyLanguages: List<String>? = null,
)

@Serializable
internal data class FlagsDto(
    val premium: Boolean = false,
    val pro: Boolean = false,
)

// -------- language file --------

@Serializable
internal data class SpyContentLangDto(
    val schema: String,
    val contentVersion: Int,
    val generatedAt: String? = null,
    val language: String,
    val sets: List<SetLangDto>,
    val words: List<WordsLangDto>,
)

@Serializable
internal data class SetLangDto(
    val key: String,
    val name: String,
)

@Serializable
internal data class WordsLangDto(
    val setKey: String,
    val items: List<String>,
)