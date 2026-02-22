package com.missclick.spy.core.model

data class OptionsResolved(
    val playersCount: Int,
    val spiesCount: Int,
    val time: Int,
    val selectedLanguageCode: String,
    val selectedSetKey: String,
    val selectedSetName: String,
    val isSelectedSetPremium: Boolean,
    val isHardModeEnabled: Boolean,
    val isPremium: Boolean,
)