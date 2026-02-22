package com.missclick.spy.core.database.content_loader

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SpyContentDto(
    val schema: String,
    val contentVersion: Int,
    val generatedAt: String? = null,
    val languages: List<LanguageDto>,
    val sets: List<SetDto>,
    val words: List<WordsDto>,
)

@Serializable
internal data class LanguageDto(
    @SerialName("key") val code: String,
    val name: String,
)

@Serializable
internal data class SetDto(
    val key: String,
    val language: String,
    val name: String,
    val flags: FlagsDto? = null,
)

@Serializable
internal data class FlagsDto(
    val premium: Boolean = false,
    val pro: Boolean = false,
)

@Serializable
internal data class WordsDto(
    val setKey: String,
    val language: String,
    val items: List<String>,
)