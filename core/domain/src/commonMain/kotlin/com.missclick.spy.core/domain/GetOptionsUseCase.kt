package com.missclick.spy.core.domain

import com.missclick.spy.core.common.Constant.MIN_LOCATIONS_TO_PLAY
import com.missclick.spy.core.data.LanguageRepo
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.model.Options
import com.missclick.spy.core.purchase.PurchaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetOptionsUseCase(
    private val optionsRepo: OptionsRepo,
    private val languageRepo: LanguageRepo,
    private val wordsRepo: WordRepo,
    private val setRepo: SetRepo,
    private val purchaseManager: PurchaseManager,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Options> {
        val premiumFlow = purchaseManager.isPremium

        return optionsRepo.options
            .combine(premiumFlow) { optionsRaw, isPremium ->
                normalizeLanguage(optionsRaw).copy(isPremium = isPremium)
            }
            .flatMapLatest { options ->
                wordsRepo.getWords(options.collectionName, options.selectedLanguageCode)
                    .map { words -> options to words.size }
            }
            .map { (options, wordsCount) ->
                val needsResetByLanguage =
                    options.collectionLanguageCode != options.selectedLanguageCode

                val needsResetByWords =
                    wordsCount < MIN_LOCATIONS_TO_PLAY

                if (needsResetByLanguage || needsResetByWords) {
                    val defaultCollection = setRepo.getDefaultSet(options.selectedLanguageCode)
                    options.copy(
                        collectionName = defaultCollection,
                        isSelectedCollectionPremium = false,
                    )
                } else {
                    options
                }
            }
            .flowOn(Dispatchers.IO)
    }

    private suspend fun normalizeLanguage(optionsRaw: Options): Options {
        if (optionsRaw.selectedLanguageCode.isNotEmpty()) return optionsRaw

        val currentLanguage = languageRepo.getCurrentLanguageCode()
        val newLanguageCode =
            if (languageRepo.checkIsExistLanguage(currentLanguage)) currentLanguage
            else languageRepo.getDefaultLanguage()

        return optionsRaw.copy(selectedLanguageCode = newLanguageCode)
    }
}

