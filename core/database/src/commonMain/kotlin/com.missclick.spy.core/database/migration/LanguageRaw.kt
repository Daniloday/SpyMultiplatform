package com.missclick.spy.core.database.migration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LanguageRaw(
    @SerialName("language_id") val languageId: Int,
    val sets: List<WordSetRaw>
)