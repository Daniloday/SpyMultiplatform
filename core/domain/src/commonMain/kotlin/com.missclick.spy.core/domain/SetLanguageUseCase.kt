package com.missclick.spy.core.domain

import com.missclick.spy.core.data.LanguageRepo
import com.missclick.spy.core.data.OptionsRepo

class SetLanguageUseCase(
    private val optionsRepo: OptionsRepo,
    private val language: LanguageRepo,
) {

    suspend operator fun invoke(languageCode: String) {
        try {
            optionsRepo.setLanguage(languageCode)
            language.setLanguage(languageCode)
        } catch (_: Throwable) {

        }
    }
}