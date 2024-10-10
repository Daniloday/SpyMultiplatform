package com.missclick.spy.feature.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.domain.GetOptionsUseCase
import com.missclick.spy.core.model.Options
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class GameViewModel(
    private val wordRepo: WordRepo,
    private val getOptionsUseCase: GetOptionsUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow<GameViewState>(GameViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val options = getOptionsUseCase().first()
            val wordResult = wordRepo.getWords(options.collectionName, options.selectedLanguageCode)
            initCards(options = options, words = wordResult.first())
        }
    }

    private fun initCards(options: Options, words: List<String>) {
        val randomWord = words.random(Random(Clock.System.now().toEpochMilliseconds()))
        val localCads = List(options.playersCount - options.spiesCount) {
            CardInfo(
                cardState = CardState.CLOSED,
                location = randomWord,
                isSpy = false
            )
        }

        val spyCards = List(options.spiesCount) {
            CardInfo(
                cardState = CardState.CLOSED,
                location = randomWord,
                isSpy = true
            )
        }

        val cards = (localCads + spyCards).shuffled(Random(Clock.System.now().toEpochMilliseconds()))

        _viewState.update {
            GameViewState.Preparing(
                cards = cards,
                timerMin = options.time
            )
        }
    }

    fun onCardClick() {
        val preparingState = viewState.value as? GameViewState.Preparing ?: return
        var done = false
        _viewState.update {
            preparingState.copy(
                cards = preparingState.cards.mapIndexed { index, cardInfo ->
                    if (done || cardInfo.cardState == CardState.SKIPPED) {
                        return@mapIndexed cardInfo
                    }
                    if (index == preparingState.cards.size - 1 && cardInfo.cardState == CardState.OPENED) runTimer()
                    done = true
                    when (cardInfo.cardState) {
                        CardState.CLOSED -> cardInfo.copy(cardState = CardState.OPENED)
                        CardState.OPENED -> cardInfo.copy(cardState = CardState.SKIPPED)
                        else -> cardInfo
                    }
                }
            )
        }
    }

    private fun runTimer() {
        viewModelScope.launch {
            delay(500)
            val preparingState = viewState.value as? GameViewState.Preparing ?: return@launch
            _viewState.update {
                GameViewState.Timer(
                    timeSecLeft = preparingState.timerMin * 60,
                    spyNumbers = preparingState.cards.mapIndexedNotNull { index, cardInfo ->
                        if (cardInfo.isSpy) return@mapIndexedNotNull index + 1
                        null
                    }
                )
            }

            var job: Job? = null
            job = viewModelScope.launch(Dispatchers.Default) {
                flow {
                    while (true) {
                        emit(Unit)
                        delay(1000)
                    }
                }.collect {
                    val timerState = viewState.value as? GameViewState.Timer
                    if (timerState == null || timerState.timeSecLeft == 0) {
                        job?.cancel()
                        return@collect
                    }

                    _viewState.value = timerState.copy(
                        timeSecLeft = timerState.timeSecLeft - 1
                    )
                }
            }
        }
    }

    fun showSpies() {
        val timerState = viewState.value as? GameViewState.Timer ?: return
        _viewState.update {
            GameViewState.End(spyNumbers = timerState.spyNumbers)
        }
    }

}

sealed class GameViewState {

    data object Loading : GameViewState()

    data class Preparing(
        val cards: List<CardInfo>,
        val timerMin: Int,
    ) : GameViewState()

    data class Timer(
        val timeSecLeft: Int,
        val spyNumbers: List<Int>,
    ) : GameViewState()

    data class End(
        val spyNumbers: List<Int>,
    ) : GameViewState()

}

data class CardInfo(
    val cardState: CardState,
    val location: String,
    val isSpy: Boolean,
)

enum class CardState {
    CLOSED, OPENED, SKIPPED
}

fun Int.toMinSec(): String {
    val duration = this.seconds
    val minutesPart = duration.inWholeMinutes
    val secondsPart = (duration - minutesPart.minutes).inWholeSeconds
    return "$minutesPart:${secondsPart.toString().padStart(2, '0')}"
}




