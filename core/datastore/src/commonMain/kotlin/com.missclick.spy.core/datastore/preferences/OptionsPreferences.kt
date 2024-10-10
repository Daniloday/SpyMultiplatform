package com.missclick.spy.core.datastore.preferences

import com.missclick.spy.core.model.Options
import kotlinx.serialization.Serializable

@Serializable
internal data class OptionsPreferences(
    val playersCount: Int,
    val spiesCount: Int,
    val time: Int,
    val selectedLanguageCode: String,
    val collectionLanguageCode: String,
    val collectionName: String,
)

internal fun OptionsPreferences.asModel() = Options(
    playersCount = playersCount,
    spiesCount = spiesCount,
    time = time,
    selectedLanguageCode = selectedLanguageCode,
    collectionName = collectionName,
    collectionLanguageCode = collectionLanguageCode
)