package com.missclick.spy.feature.words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.domain.GetOptionsUseCase
import com.missclick.spy.core.model.Set
import com.missclick.spy.core.model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordsViewModel(
    private val optionsRepo: OptionsRepo,
    private val getOptionsUseCase: GetOptionsUseCase,
    private val wordsRepo: WordRepo,
    private val setRepo: SetRepo,
) : ViewModel() {


    private val _viewState = MutableStateFlow<WordsViewState>(WordsViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun loadData(selectedCollectionKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val options = getOptionsUseCase().first()
            val selectedSet = setRepo.getSetOrNull(selectedCollectionKey, options.selectedLanguageCode) ?: return@launch
            val getWordsResult = wordsRepo.getWords(selectedCollectionKey, options.selectedLanguageCode)
            getWordsResult.collect {
                initSuccess(selectedSet, it, options.selectedLanguageCode)
            }
        }
    }

    private fun initSuccess(selectedSet: Set, words: List<String>, languageCode: String) {
        val successState = viewState.value as? WordsViewState.Success
        _viewState.update {
            WordsViewState.Success(
                words = words,
                isEnteringNewWord = successState?.isEnteringNewWord ?: false,
                newWord = successState?.newWord ?: "",
                collectionName = selectedSet.name,
                isEditable = selectedSet.isCustom,
                isPremium = selectedSet.isPremium,
                setKey = selectedSet.key,
                languageCode = languageCode
            )
        }
    }

    suspend fun saveCollection() {
        withContext(Dispatchers.IO) {
            val successState = viewState.value as? WordsViewState.Success ?: return@withContext
            optionsRepo.setSelectedSet(
                setKey = successState.setKey
            )
        }
    }

    fun deleteCollection() {
        val successState = viewState.value as? WordsViewState.Success ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val options = getOptionsUseCase().first()
            setRepo.deleteSet(successState.setKey, options.selectedLanguageCode)
        }
    }

    fun deleteWord(name: String) {
        val successState = viewState.value as? WordsViewState.Success ?: return
        viewModelScope.launch(Dispatchers.IO) {
            wordsRepo.deleteWord(
                wordText = name,
                setKey = successState.setKey,
                languageCode = successState.languageCode
            )
        }
    }

    fun addWord() {
        val successState = viewState.value as? WordsViewState.Success ?: return
        _viewState.update {
            successState.copy(
                isEnteringNewWord = true,
                newWord = ""
            )
        }
    }

    fun saveNewWord() {
        val successState = viewState.value as? WordsViewState.Success ?: return
        if (successState.newWord.isNotBlank()) {
            val word = Word(
                wordName = successState.newWord,
                isHidden = false
            )
            viewModelScope.launch(Dispatchers.IO) {
                val options = getOptionsUseCase().first()
                wordsRepo.addWord(word, successState.setKey, options.selectedLanguageCode)
            }
        }
        _viewState.update {
            successState.copy(
                isEnteringNewWord = false,
                newWord = ""
            )
        }
    }

    fun onNewWordChange(newWord: String) {
        val successState = viewState.value as? WordsViewState.Success ?: return
        _viewState.update {
            successState.copy(
                newWord = newWord
            )
        }
    }

}

sealed class WordsViewState {
    data object Loading : WordsViewState()
    data class Success(
        val collectionName: String,
        val setKey: String,
        val languageCode: String,
        val isEnteringNewWord: Boolean = false,
        val newWord: String = "",
        val isEditable: Boolean,
        val words: List<String>,
        val isPremium: Boolean
    ) : WordsViewState()
}