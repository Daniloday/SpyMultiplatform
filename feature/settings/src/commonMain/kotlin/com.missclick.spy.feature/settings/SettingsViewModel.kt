package com.missclick.spy.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.domain.GetOptionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getOptionsUseCase: GetOptionsUseCase,
    private val optionsRepo: OptionsRepo,
    private val wordRepo: WordRepo,
): ViewModel() {

    private val _viewState = MutableStateFlow(SettingsViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            wordRepo.preload()
            val languages = wordRepo.getLanguages()
            getOptionsUseCase().collect { options ->
                _viewState.update { state ->
                    state.copy(
                        languages = languages.map {
                            LanguageView(
                                name = it.name,
                                code = it.code,
                                isSelected = it.code == options.selectedLanguageCode
                            )
                        }
                    )
                }
            }
        }
    }

    fun selectLanguage(
        selectedLanguage: LanguageView,
    ) {
        viewModelScope.launch {
            optionsRepo.setLanguage(selectedLanguage.code)
        }
    }

}

data class SettingsViewState(
    val languages: List<LanguageView> = emptyList()
)

data class LanguageView(
    val name: String,
    val code: String,
    val isSelected: Boolean = false,
)