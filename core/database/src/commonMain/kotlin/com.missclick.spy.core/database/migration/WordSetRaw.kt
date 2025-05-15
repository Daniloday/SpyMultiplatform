package com.missclick.spy.core.database.migration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordSetRaw(
    val name: String,
    @SerialName("is_premium") val isPremium: Boolean,
    @SerialName("is_pro") val isPro: Boolean,
    val words: List<String>
)