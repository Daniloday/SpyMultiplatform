package com.missclick.spy.core.domain

import com.missclick.spy.core.data.DeviceRepo
import com.missclick.spy.core.data.OptionsRepo

class SetLanguageUseCase(
    private val optionsRepo: OptionsRepo,
    private val deviceRepo: DeviceRepo,
) {

    suspend operator fun invoke(languageCode: String) {
        try {
            optionsRepo.setLanguage(languageCode)
            deviceRepo.setLanguage(languageCode)
        } catch (_: Throwable) {

        }
    }
}