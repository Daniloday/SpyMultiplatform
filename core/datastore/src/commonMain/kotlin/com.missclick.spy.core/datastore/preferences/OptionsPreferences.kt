package com.missclick.spy.core.datastore.preferences

import com.missclick.spy.core.common.Constant.PLAYERS_DEFAULT
import com.missclick.spy.core.common.Constant.SPIES_DEFAULT
import com.missclick.spy.core.common.Constant.TIMER_DEFAULT
import com.missclick.spy.core.model.OptionsStored
import kotlinx.serialization.Serializable



@Serializable
internal data class OptionsPreferences(
    val playersCount: Int = PLAYERS_DEFAULT,
    val spiesCount: Int = SPIES_DEFAULT,
    val time: Int = TIMER_DEFAULT,
    val selectedLanguageCode: String = "",
    val selectedSetKey: String = "",
    val isHardModeEnabled: Boolean = false,
)

internal fun OptionsPreferences.asModel() = OptionsStored(
    playersCount = playersCount,
    spiesCount = spiesCount,
    time = time,
    selectedLanguageCode = selectedLanguageCode,
    selectedSetKey = selectedSetKey,
    isHardModeEnabled = isHardModeEnabled,
)