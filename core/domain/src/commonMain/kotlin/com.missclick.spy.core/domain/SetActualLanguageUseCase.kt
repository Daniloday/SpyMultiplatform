package com.missclick.spy.core.domain

import com.missclick.spy.core.data.DeviceRepo
import com.missclick.spy.core.data.OptionsRepo
import kotlinx.coroutines.flow.first

class SetActualLanguageUseCase(
    private val optionsRepo: OptionsRepo,
    private val deviceRepo: DeviceRepo,
) {

    suspend operator fun invoke() {
        try {
            val options = optionsRepo.options.first()
            val selectedLanguage = options.selectedLanguageCode
            if (selectedLanguage.isNotEmpty()) {
                deviceRepo.setLanguage(selectedLanguage)
            }
        } catch (_: Throwable) {

        }
    }
}