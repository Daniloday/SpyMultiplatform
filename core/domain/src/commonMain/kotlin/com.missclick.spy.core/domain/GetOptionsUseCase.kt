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
            .mapLatest { ensureLanguage(it) }
            .mapLatest { ensureSelectedSetKey(it) }
            .combine(purchaseManager.isPremium) { stored, isPremium -> stored to isPremium }
            .mapLatest { (stored, isPremium) -> resolveAndValidate(stored, isPremium) }
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
    }

    private suspend fun ensureLanguage(raw: OptionsStored): OptionsStored {
        val current = normLang(raw.selectedLanguageCode)
        if (current.isNotBlank()) return raw.copy(selectedLanguageCode = current)

        val deviceLang = normLang(languageRepo.getCurrentLanguageCode())
        val actualLang = if (languageRepo.checkIsExistLanguage(deviceLang)) deviceLang else DEFAULT_LANG

        optionsRepo.setLanguage(actualLang)
        return raw.copy(selectedLanguageCode = actualLang)
    }

    private suspend fun ensureSelectedSetKey(stored: OptionsStored): OptionsStored {
        val currentKey = normKey(stored.selectedSetKey)
        if (currentKey.isNotBlank()) return stored.copy(selectedSetKey = currentKey)

        // Ставим хотя бы basic, чтобы не было пустого ключа вообще
        optionsRepo.setSelectedSet(BASIC_SET_KEY)
        return stored.copy(selectedSetKey = BASIC_SET_KEY)
    }

    private suspend fun resolveAndValidate(
        stored: OptionsStored,
        isPremium: Boolean,
    ): OptionsResolved {
        val lang = normLang(stored.selectedLanguageCode).ifBlank { DEFAULT_LANG }
        val requestedKey = normKey(stored.selectedSetKey).ifBlank { BASIC_SET_KEY }

        val resolved = resolveSetAndWords(
            requestedKey = requestedKey,
            requestedLang = lang,
        )

        if (resolved.languageCode != lang) {
            optionsRepo.setLanguage(resolved.languageCode)
        }
        if (resolved.setKey != requestedKey) {
            optionsRepo.setSelectedSet(resolved.setKey)
        }

        val setName = resolved.set?.name ?: BASIC_SET_KEY
        val isSetPremium = resolved.set?.isPremium ?: false

        return OptionsResolved(
            playersCount = stored.playersCount,
            spiesCount = stored.spiesCount,
            time = stored.time,
            selectedLanguageCode = resolved.languageCode,
            selectedSetName = setName,
            selectedSetKey = resolved.setKey,
            isSelectedSetPremium = isSetPremium,
            isHardModeEnabled = stored.isHardModeEnabled,
            isPremium = isPremium,
        )
    }

    private data class Resolved(
        val languageCode: String,
        val setKey: String,
        val set: com.missclick.spy.core.model.Set?,
        val wordsCount: Int,
    )

    private suspend fun resolveSetAndWords(
        requestedKey: String,
        requestedLang: String,
    ): Resolved {
        // 1) key+lang
        resolveCandidate(requestedKey, requestedLang)?.let { return it }

        // 2) basic+lang
        resolveCandidate(BASIC_SET_KEY, requestedLang)?.let { return it }

        // 3) basic+en
        resolveCandidate(BASIC_SET_KEY, DEFAULT_LANG)?.let { return it }

        return Resolved(
            languageCode = DEFAULT_LANG,
            setKey = BASIC_SET_KEY,
            set = null,
            wordsCount = 0,
        )
    }

    private suspend fun resolveCandidate(setKey: String, languageCode: String): Resolved? {
        // IMPORTANT: тут нужен SetRepo.getSetOrNull (не падающий)
        val set = setRepo.getSetOrNull(setKey = setKey, languageCode = languageCode) ?: return null

        // wordsRepo.getWords если сет не найден - обычно вернет пусто, но мы уже проверили set != null
        val wordsCount = wordsRepo.getWords(setKey = setKey, languageCode = languageCode).first().size

        // если слов мало - считаем кандидат невалидным (будет fallback дальше)
        if (wordsCount < MIN_LOCATIONS_TO_PLAY) return null

        return Resolved(
            languageCode = languageCode,
            setKey = setKey,
            set = set,
            wordsCount = wordsCount,
        )
    }

    private companion object {
        const val DEFAULT_LANG = "en"
        const val BASIC_SET_KEY = "basic"

        fun normKey(v: String) = v.trim().lowercase()
        fun normLang(v: String) = v
    }
}