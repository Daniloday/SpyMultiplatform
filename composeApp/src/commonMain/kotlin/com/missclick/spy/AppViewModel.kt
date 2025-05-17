package com.missclick.spy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.core.domain.GetOptionsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class AppViewModel(
    getOptionsUseCase: GetOptionsUseCase,
) : ViewModel() {

    val isPremium: StateFlow<Boolean> = getOptionsUseCase().map {
        it.isPremium
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = true,
    )

}