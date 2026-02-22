package com.missclick.spy.core.model

data class OptionsStored(
    val playersCount: Int,
    val spiesCount: Int,
    val time: Int,
    val selectedLanguageCode: String,
    val selectedSetKey: String,
    val isHardModeEnabled: Boolean,
)