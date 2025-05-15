package com.missclick.spy.feature.game_options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.domain.GetOptionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameOptionsViewModel(
    private val optionsRepo: OptionsRepo,
    getOptionsUseCase: GetOptionsUseCase
) : ViewModel() {

    val viewStateOptions: StateFlow<GameOptionsViewStateOptions> = getOptionsUseCase().map {
        GameOptionsViewStateOptions.Success(
            playersCount = it.playersCount,
            spiesCount = it.spiesCount,
            time = it.time,
            collectionName = it.collectionName,
            isPremium = it.isPremium,
            isSelectedCollectionPremium = it.isSelectedCollectionPremium
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GameOptionsViewStateOptions.Loading,
    )

    private val _viewStateScreen = MutableStateFlow(GameOptionsViewStateScreen())
    val viewStateScreen = _viewStateScreen.asStateFlow()

    fun onUpPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewStateOptions.value as? GameOptionsViewStateOptions.Success ?: return@launch
            val newPlayersCount = viewState.playersCount + 1
            optionsRepo.setPlayersCount(newPlayersCount)
        }
    }

    fun onDownPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewStateOptions.value as? GameOptionsViewStateOptions.Success ?: return@launch
            val newPlayersCount = viewState.playersCount - 1
            optionsRepo.setPlayersCount(newPlayersCount)
            if (viewState.spiesCount == newPlayersCount) {
                val newSpiesCount = viewState.spiesCount - 1
                optionsRepo.setSpiesCount(newSpiesCount)
            }
        }
    }

    fun onUpTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewStateOptions.value as? GameOptionsViewStateOptions.Success ?: return@launch
            val newTime = viewState.time + 1
            optionsRepo.setTime(newTime)
        }
    }

    fun onDownTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewStateOptions.value as? GameOptionsViewStateOptions.Success ?: return@launch
            val newTime = viewState.time - 1
            optionsRepo.setTime(newTime)
        }
    }

    fun onUpSpies() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewStateOptions.value as? GameOptionsViewStateOptions.Success ?: return@launch
            val newSpiesCount = viewState.spiesCount + 1
            optionsRepo.setSpiesCount(newSpiesCount)
        }
    }

    fun onDownSpies() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewStateOptions.value as? GameOptionsViewStateOptions.Success ?: return@launch
            val newSpiesCount = viewState.spiesCount - 1
            optionsRepo.setSpiesCount(newSpiesCount)
        }
    }

    fun onShowPremiumSetDialog() {
        _viewStateScreen.update {
            it.copy(
                isShowPremiumSetDialog = true
            )
        }
    }

    fun onClosePremiumSetDialog() {
        _viewStateScreen.update {
            it.copy(
                isShowPremiumSetDialog = false
            )
        }
    }

}

sealed class GameOptionsViewStateOptions {
    data object Loading : GameOptionsViewStateOptions()
    data class Success(
        val playersCount: Int,
        val spiesCount: Int,
        val time: Int,
        val collectionName: String,
        val isPremium: Boolean,
        val isSelectedCollectionPremium: Boolean,
        val isShowPremiumSetDialog: Boolean = false
    ) : GameOptionsViewStateOptions()
}

data class GameOptionsViewStateScreen(
    val isShowPremiumSetDialog: Boolean = false,
)