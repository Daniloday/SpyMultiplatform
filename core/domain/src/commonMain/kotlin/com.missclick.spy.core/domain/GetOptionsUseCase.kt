package com.missclick.spy.core.domain

import com.missclick.spy.core.common.Constant.MIN_LOCATIONS_TO_PLAY
import com.missclick.spy.core.data.LanguageRepo
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.model.OptionsResolved
import com.missclick.spy.core.model.OptionsStored
import com.missclick.spy.core.purchase.PurchaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*

class GetOptionsUseCase(
    private val optionsRepo: OptionsRepo,
    private val languageRepo: LanguageRepo,
    private val wordsRepo: WordRepo,
    private val setRepo: SetRepo,
    private val purchaseManager: PurchaseManager,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<OptionsResolved> {
        return optionsRepo.options
            .flatMapLatest { raw ->
                flow { emit(ensureLanguage(raw)) }
            }
            .flatMapLatest { stored ->
                flow { emit(ensureSelectedSetKey(stored)) }
            }
            .combine(purchaseManager.isPremium) { stored, isPremium ->
                stored to isPremium
            }
            .flatMapLatest { (stored, isPremium) ->
                flow {
                    val resolved = resolveAndValidate(stored, isPremium)
                    emit(resolved)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    private suspend fun ensureLanguage(raw: OptionsStored): OptionsStored {
        if (raw.selectedLanguageCode.isNotBlank()) return raw

        val deviceLang = languageRepo.getCurrentLanguageCode()
        val actualLang =
            if (languageRepo.checkIsExistLanguage(deviceLang)) deviceLang
            else "en"

        optionsRepo.setLanguage(actualLang)

        return raw.copy(selectedLanguageCode = actualLang)
    }

    private suspend fun ensureSelectedSetKey(stored: OptionsStored): OptionsStored {
        if (stored.selectedSetKey.isNotBlank()) return stored

        val defaultKey = setRepo.getDefaultSet(stored.selectedLanguageCode)
        optionsRepo.setSelectedSet(defaultKey)

        return stored.copy(selectedSetKey = defaultKey)
    }

    private suspend fun resolveAndValidate(
        stored: OptionsStored,
        isPremium: Boolean,
    ): OptionsResolved {
        // Resolve set
        val set = setRepo.getSet(
            setKey = stored.selectedSetKey,
            languageCode = stored.selectedLanguageCode
        )

        // Validate words
        val wordsCount = wordsRepo.getWords(
            setKey = stored.selectedSetKey,
            languageCode = stored.selectedLanguageCode
        ).first().size

        if (wordsCount < MIN_LOCATIONS_TO_PLAY) {
            val defaultKey = setRepo.getDefaultSet(stored.selectedLanguageCode)
            if (defaultKey != stored.selectedSetKey) {
                optionsRepo.setSelectedSet(defaultKey)
            }

            val defaultSet = setRepo.getSet(
                setKey = defaultKey,
                languageCode = stored.selectedLanguageCode
            )

            return OptionsResolved(
                playersCount = stored.playersCount,
                spiesCount = stored.spiesCount,
                time = stored.time,

                selectedLanguageCode = stored.selectedLanguageCode,
                selectedSetName = defaultSet.name,
                selectedSetKey = stored.selectedSetKey,
                isSelectedSetPremium = defaultSet.isPremium,
                isHardModeEnabled = stored.isHardModeEnabled,
                isPremium = isPremium,
            )
        }

        return OptionsResolved(
            playersCount = stored.playersCount,
            spiesCount = stored.spiesCount,
            time = stored.time,
            selectedLanguageCode = stored.selectedLanguageCode,
            selectedSetName = set.name,
            selectedSetKey = stored.selectedSetKey,
            isSelectedSetPremium = set.isPremium,
            isHardModeEnabled = stored.isHardModeEnabled,
            isPremium = isPremium,
        )
    }
}