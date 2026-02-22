package com.missclick.spy.core.domain

import com.missclick.spy.core.data.LanguageRepo
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.model.Set
import kotlin.time.Clock

class CreateNewSetUseCase(
    private val setRepo: SetRepo,
) {

    suspend operator fun invoke(setName: String, languageCode: String): Set? {
        return try {

            val slug = setName
                .lowercase()
                .replace(Regex("[^a-z0-9]+"), "_")
                .trim('_')

            val set = Set(
                name = setName,
                key = "custom_${slug}_${Clock.System.now().toEpochMilliseconds()}",
                isCustom = true,
                isPremium = false,
                isPro = false,
            )
            setRepo.addSet(set, languageCode)
            return set
        } catch (_: Throwable) {
            null
        }
    }
}