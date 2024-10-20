package com.missclick.spy.core.domain

import com.missclick.spy.core.common.Constant.MIN_LOCATIONS_TO_PLAY
import com.missclick.spy.core.data.LanguageRepo
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.model.Options
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetOptionsUseCase(
    private val optionsRepo: OptionsRepo,
    private val languageRepo: LanguageRepo,
    private val wordsRepo: WordRepo,
    private val setRepo: SetRepo,
) {

    operator fun invoke(): Flow<Options> = optionsRepo.options.map { optionsRaw ->
        val options = if (optionsRaw.selectedLanguageCode.isNotEmpty()) {
            optionsRaw
        } else {
            val currentLanguage = languageRepo.getCurrentLanguageCode()
            val isExistCurrentLanguage = languageRepo.checkIsExistLanguage(currentLanguage)
            val newLanguageCode = if (isExistCurrentLanguage) {
                currentLanguage
            } else {
                languageRepo.getDefaultLanguage()
            }
            optionsRaw.copy(
                selectedLanguageCode = newLanguageCode
            )
        }

        val words = wordsRepo.getWords(options.collectionName, options.selectedLanguageCode).first()

        if (
            options.collectionLanguageCode != options.selectedLanguageCode
            || words.size < MIN_LOCATIONS_TO_PLAY
        ) {
            val defaultCollection = setRepo.getDefaultSet(options.selectedLanguageCode)
            return@map options.copy(collectionName = defaultCollection)
        }

        options
    }

}
