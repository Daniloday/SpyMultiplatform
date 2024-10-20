package com.missclick.spy.core.domain

import com.missclick.spy.core.data.LanguageRepo
import com.missclick.spy.core.data.OptionsRepo
import kotlinx.coroutines.flow.first

class SetActualLanguageUseCase(
    private val optionsRepo: OptionsRepo,
    private val languageRepo: LanguageRepo,
) {

    suspend operator fun invoke() {
        try {
            val options = optionsRepo.options.first()
            val selectedLanguage = options.selectedLanguageCode
            if (selectedLanguage.isNotEmpty()) {
                languageRepo.setLanguage(selectedLanguage)
            }
        } catch (_: Throwable) {

        }
    }
}