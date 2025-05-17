package com.missclick.spy.feature.premium

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.domain.GetOptionsUseCase
import com.missclick.spy.core.purchase.PurchaseManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class PremiumViewModel(
    private val purchaseManager: PurchaseManager,
    private val getOptions: GetOptionsUseCase,
    private val optionsRepo: OptionsRepo,
): ViewModel() {

    val viewState = getOptions().map {
        if (it.isPremium) PremiumViewState.Premium else PremiumViewState.NoPremium
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PremiumViewState.Loading,
    )

    fun onBuy() {
        purchaseManager.buy { result ->
            if (result) {
                viewModelScope.launch {
                    optionsRepo.activatePremium()
                }
            }
        }
    }

    fun onRestore() {
        purchaseManager.restore { result ->
            if (result) {
                viewModelScope.launch {
                    optionsRepo.activatePremium()
                }
            }
        }
    }

}

sealed class PremiumViewState {
    data object Loading : PremiumViewState()
    data object Premium : PremiumViewState()
    data object NoPremium : PremiumViewState()
}