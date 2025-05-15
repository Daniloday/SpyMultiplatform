package com.missclick.spy.core.datastore.preferences

import com.missclick.spy.core.common.Constant.PLAYERS_DEFAULT
import com.missclick.spy.core.common.Constant.SPIES_DEFAULT
import com.missclick.spy.core.common.Constant.TIMER_DEFAULT
import com.missclick.spy.core.model.Options
import kotlinx.serialization.Serializable

@Serializable
internal data class OptionsPreferences(
    val playersCount: Int = PLAYERS_DEFAULT,
    val spiesCount: Int = SPIES_DEFAULT,
    val time: Int = TIMER_DEFAULT,
    val selectedLanguageCode: String = "",
    val isSelectedCollectionPremium: Boolean = false,
    val collectionLanguageCode: String = "",
    val collectionName: String = "",
    val isPremium: Boolean = false,
)

internal fun OptionsPreferences.asModel() = Options(
    playersCount = playersCount,
    spiesCount = spiesCount,
    time = time,
    selectedLanguageCode = selectedLanguageCode,
    collectionName = collectionName,
    collectionLanguageCode = collectionLanguageCode,
    isPremium = isPremium,
    isSelectedCollectionPremium = isSelectedCollectionPremium,
)