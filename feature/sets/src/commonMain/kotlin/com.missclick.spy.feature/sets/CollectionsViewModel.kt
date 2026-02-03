package com.missclick.spy.feature.sets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.domain.GetOptionsUseCase
import com.missclick.spy.core.model.Set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionsViewModel(
    private val wordRepo: WordRepo,
    private val setRepo: SetRepo,
    private val getOptionsUseCase: GetOptionsUseCase,
    private val optionRepo: OptionsRepo,
) : ViewModel() {

    private val _viewState = MutableStateFlow<CollectionsViewState>(CollectionsViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val options = getOptionsUseCase().first()
            val selectedCollection = options.collectionName
            setRepo.getSets(options.selectedLanguageCode).collect { sets ->
                initSuccess(
                    selectedCollection = selectedCollection,
                    sets = sets.sortedBy {
                        it.isPremium
                    }.sortedBy {
                        it.isPro
                    },
                    isPremium = optionRepo.options.first().isPremium
                )
            }
        }
    }


    private fun initSuccess(
        sets: List<Set>,
        selectedCollection: String,
        isPremium: Boolean,
    ) {
        val collectionViews = sets.map { set ->
            CollectionView(
                name = set.name,
                isSelected = set.name == selectedCollection,
                isPremium = set.isPremium,
                isPro = set.isPro
            )
        }
        val successState = viewState.value as? CollectionsViewState.Success
        _viewState.update {
            CollectionsViewState.Success(
                collectionViews = collectionViews,
                isEnteringNewCollection = successState?.isEnteringNewCollection ?: false,
                newCollection = successState?.newCollection ?: "",
                isPremium = isPremium,
            )
        }
    }

    fun addNewCollection() {
        val successState = viewState.value as? CollectionsViewState.Success ?: return
        _viewState.update {
            successState.copy(
                isEnteringNewCollection = true,
                newCollection = ""
            )
        }
    }

    fun saveNewCollection() {
        val successState = viewState.value as? CollectionsViewState.Success ?: return
        if (successState.newCollection.isNotBlank()) {
            val newSet = Set(
                name = successState.newCollection,
                isCustom = true,
                isPremium = false,
                isPro = false,
            )
            viewModelScope.launch(Dispatchers.IO) {
                val options = getOptionsUseCase().first()
                setRepo.addSet(
                    newSet,
                    options.selectedLanguageCode
                )
            }
        }
        _viewState.update {
            successState.copy(
                isEnteringNewCollection = false,
                newCollection = ""
            )
        }
    }

    fun onNewCollectionNameChange(newName: String) {
        val successState = viewState.value as? CollectionsViewState.Success ?: return
        _viewState.update {
            successState.copy(
                newCollection = newName
            )
        }
    }

}

sealed class CollectionsViewState {
    data object Loading: CollectionsViewState()
    data class Success(
        val collectionViews: List<CollectionView>,
        val isEnteringNewCollection: Boolean,
        val newCollection: String,
        val isPremium: Boolean,
    ): CollectionsViewState()
}

data class CollectionView(
    val name: String,
    val isSelected: Boolean,
    val isPremium: Boolean,
    val isPro: Boolean,
)