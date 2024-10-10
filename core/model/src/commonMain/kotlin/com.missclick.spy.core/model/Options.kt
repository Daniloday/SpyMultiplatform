package com.missclick.spy.core.model

data class Options(
    val playersCount: Int,
    val spiesCount: Int,
    val time: Int,
    val collectionName: String,
    val collectionLanguageCode: String,
    val selectedLanguageCode: String,
)