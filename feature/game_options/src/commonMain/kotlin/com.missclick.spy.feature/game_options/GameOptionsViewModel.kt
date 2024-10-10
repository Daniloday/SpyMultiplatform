package com.missclick.spy.feature.game_options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.domain.GetOptionsUseCase
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameOptionsViewModel(
    private val optionsRepo: OptionsRepo,
    getOptionsUseCase: GetOptionsUseCase
) : ViewModel() {

    val viewState: StateFlow<GameOptionsViewState> = getOptionsUseCase().map {
        GameOptionsViewState.Success(
            playersCount = it.playersCount,
            spiesCount = it.spiesCount,
            time = it.time,
            collectionName = it.collectionName
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GameOptionsViewState.Loading,
    )

    fun onUpPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewState.value as? GameOptionsViewState.Success ?: return@launch
            val newPlayersCount = viewState.playersCount + 1
            optionsRepo.setPlayersCount(newPlayersCount)
        }
    }

    fun onDownPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewState.value as? GameOptionsViewState.Success ?: return@launch
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
            val viewState = viewState.value as? GameOptionsViewState.Success ?: return@launch
            val newTime = viewState.time + 1
            optionsRepo.setTime(newTime)
        }
    }

    fun onDownTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewState.value as? GameOptionsViewState.Success ?: return@launch
            val newTime = viewState.time - 1
            optionsRepo.setTime(newTime)
        }
    }

    fun onUpSpies() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewState.value as? GameOptionsViewState.Success ?: return@launch
            val newSpiesCount = viewState.spiesCount + 1
            optionsRepo.setSpiesCount(newSpiesCount)
        }
    }

    fun onDownSpies() {
        viewModelScope.launch(Dispatchers.IO) {
            val viewState = viewState.value as? GameOptionsViewState.Success ?: return@launch
            val newSpiesCount = viewState.spiesCount - 1
            optionsRepo.setSpiesCount(newSpiesCount)
        }
    }


}

sealed class GameOptionsViewState {
    data object Loading : GameOptionsViewState()
    data class Success(
        val playersCount: Int,
        val spiesCount: Int,
        val time: Int,
        val collectionName: String,
    ) : GameOptionsViewState()
}