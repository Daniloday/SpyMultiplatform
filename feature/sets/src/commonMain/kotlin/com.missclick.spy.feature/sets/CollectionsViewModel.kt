package com.missclick.spy.feature.sets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.domain.CreateNewSetUseCase
import com.missclick.spy.core.domain.GetOptionsUseCase
import com.missclick.spy.core.model.Set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectionsViewModel(
    private val setRepo: SetRepo,
    private val getOptionsUseCase: GetOptionsUseCase,
    private val createNewSetUseCase: CreateNewSetUseCase,
) : ViewModel() {



    private val _collectionsViewDraft = MutableStateFlow(CollectionsViewDraft())
    val collectionsViewDraft = _collectionsViewDraft.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState: StateFlow<CollectionsViewState> =
        combine(
            getOptionsUseCase(),
            collectionsViewDraft
        ) { options, draft ->
            options to draft
        }
            .flatMapLatest { (options, draft) ->
                setRepo.getSets(options.selectedLanguageCode)
                    .map { sets -> Triple(options, draft, sets) }
            }
            .map { (options, draft, sets) ->
                val sorted = sets.sortedWith(
                    compareBy<Set> { it.isPremium }
                        .thenBy { it.isPro }
                )

                val collectionViews = sorted.map { set ->
                    CollectionView(
                        name = set.name,
                        isSelected = set.name == options.selectedSetName,
                        isPremium = set.isPremium,
                        isPro = set.isPro,
                        key = set.key
                    )
                }

                CollectionsViewState.Success(
                    collectionViews = collectionViews,
                    isEnteringNewCollection = draft.isEnteringNewCollection,
                    isPremium = options.isPremium
                ) as CollectionsViewState
            }
            .onStart { emit(CollectionsViewState.Loading) }
            .catch { e -> emit(CollectionsViewState.Error(e.message ?: "Unknown error")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CollectionsViewState.Loading
            )

    fun addNewCollection() {
        _collectionsViewDraft.update { it.copy(isEnteringNewCollection = true, newCollection = "") }
    }

    fun onNewCollectionNameChange(newName: String) {
        _collectionsViewDraft.update { it.copy(newCollection = newName) }
    }

    suspend fun saveNewCollection(): String? {
        return withContext(Dispatchers.IO) {
            val draft = collectionsViewDraft.value
            if (draft.newCollection.isBlank()) {
                _collectionsViewDraft.update { it.copy(isEnteringNewCollection = false, newCollection = "") }
                return@withContext null
            }
            val options = getOptionsUseCase().first()
            val newSet = createNewSetUseCase(draft.newCollection, options.selectedLanguageCode)
            _collectionsViewDraft.update { it.copy(isEnteringNewCollection = false, newCollection = "") }
            return@withContext newSet?.key
        }
    }
}

sealed class CollectionsViewState {
    data object Loading : CollectionsViewState()

    data class Success(
        val collectionViews: List<CollectionView>,
        val isEnteringNewCollection: Boolean,
        val isPremium: Boolean,
    ) : CollectionsViewState()

    data class Error(val message: String) : CollectionsViewState()
}

data class CollectionView(
    val name: String,
    val isSelected: Boolean,
    val isPremium: Boolean,
    val isPro: Boolean,
    val key: String
)

data class CollectionsViewDraft(
    val isEnteringNewCollection: Boolean = false,
    val newCollection: String = "",
)
