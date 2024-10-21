package com.missclick.spy.feature.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.missclick.spy.core.advertising.InterstitialAdManager
import com.missclick.spy.core.common.extentions.randomByTime
import com.missclick.spy.core.data.AppStoreRepo
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.core.ui.kit.buttons.PrimaryButton
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.find
import com.missclick.spy.resources.hint_timer
import com.missclick.spy.resources.ic_back
import com.missclick.spy.resources.ic_hat
import com.missclick.spy.resources.ic_location
import com.missclick.spy.resources.next
import com.missclick.spy.resources.player
import com.missclick.spy.resources.tap_to_see_you_role
import com.missclick.spy.resources.was_spy
import com.missclick.spy.resources.were_spies
import com.missclick.spy.resources.you_local
import com.missclick.spy.resources.you_spy
import com.missclick.spy.resources.you_spy_hint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GameRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    vm: GameViewModel = koinViewModel(),
    interstitialAdManager: InterstitialAdManager = koinInject(),
    appStoreRepo: AppStoreRepo = koinInject()
) {

    val viewState by vm.viewState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    GameScreen(
        modifier = modifier,
        viewState = viewState,
        onBackClick = {
            if (viewState is GameViewState.End) {
                if ((0..4).randomByTime() == 0) {
                    coroutineScope.launch {
                        appStoreRepo.requestRateUs()
                        onBackClick()
                    }
                } else {
                    interstitialAdManager.showAd(onAdClosed = {
                        coroutineScope.launch {
                            onBackClick()
                        }
                    })
                }
            } else {
                onBackClick()
            }
        },
        onCardClick = vm::onCardClick,
        onFindOutClick = vm::showSpies,
    )
}

@Composable
private fun GameScreen(
    modifier: Modifier = Modifier,
    viewState: GameViewState,
    onBackClick: () -> Unit,
    onCardClick: () -> Unit,
    onFindOutClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        TopBar(onBackClick = onBackClick)
        when (viewState) {
            is GameViewState.Loading -> Unit
            is GameViewState.Preparing -> Preparing(
                onCardClick = onCardClick,
                viewState = viewState
            )

            is GameViewState.Timer -> Timer(
                leftTime = viewState.timeSecLeft,
                onFindOutClick = onFindOutClick
            )

            is GameViewState.End -> End(
                viewState = viewState,
                onNextClick = onBackClick
            )
        }

    }
}

@Composable
private fun End(
    modifier: Modifier = Modifier,
    viewState: GameViewState.End,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier,
            text = if (viewState.spyNumbers.size == 1)
                stringResource(resource = Res.string.was_spy)
            else
                stringResource(resource = Res.string.were_spies),
            color = AppTheme.colors.primary,
            style = AppTheme.types.h28,
        )
        Spacer(modifier = Modifier.height(32.dp))

        viewState.spyNumbers.forEach { spyNumber ->
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "${stringResource(resource = Res.string.player)} $spyNumber",
                style = AppTheme.types.h30,
                color = AppTheme.colors.secondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }


        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            onClick = onNextClick,
            text = stringResource(resource = Res.string.next)
        )
    }
}

@Composable
private fun Timer(
    modifier: Modifier = Modifier,
    leftTime: Int,
    onFindOutClick: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(104.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {
                val timeParts = leftTime.toMinSec().split(":")
                Text(
                    modifier = Modifier.weight(4f),
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    text = timeParts[0] + ":",
                    color = AppTheme.colors.secondary,
                    style = AppTheme.types.h168,
                )
                Text(
                    modifier = Modifier.weight(5f),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    text = timeParts[1],
                    color = AppTheme.colors.secondary,
                    style = AppTheme.types.h168,
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(resource = Res.string.hint_timer),
                style = AppTheme.types.h20,
                color = AppTheme.colors.primary,
                textAlign = TextAlign.Center
            )

            PrimaryButton(
                onClick = onFindOutClick,
                text = stringResource(resource = Res.string.find)
            )
        }
    }
}

@Composable
private fun Preparing(
    modifier: Modifier = Modifier,
    viewState: GameViewState.Preparing,
    onCardClick: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        viewState.cards.reversed().forEachIndexed() { cardIndexReversed, cardInfo ->
            val cardIndex = viewState.cards.size - 1 - cardIndexReversed
            val cardOffset by animateDpAsState(
                targetValue = 4.dp * (cardIndex - viewState.cards.count { it.cardState == CardState.SKIPPED }),
                animationSpec = tween(600),
                label = ""
            )

            GameCard(
                modifier = Modifier
                    .offset(x = cardOffset, y = -cardOffset)
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth(0.9f)
                    .align(Alignment.Center),
                cardInfo = cardInfo,
                onCardClick = onCardClick,
                playerNumber = cardIndex + 1
            )
        }

    }
}

@Composable
private fun GameCard(
    modifier: Modifier = Modifier,
    cardInfo: CardInfo,
    playerNumber: Int,
    onCardClick: () -> Unit,
) {


    val offset by animateDpAsState(
        targetValue = if (cardInfo.cardState != CardState.SKIPPED) 0.dp else 560.dp,
        animationSpec = tween(600),
        label = ""
    )

    val rotationZ by animateFloatAsState(
        targetValue = if (cardInfo.cardState != CardState.SKIPPED) 0f else -20f,
        animationSpec = tween(600),
        label = ""
    )

    val rotationY by animateFloatAsState(
        targetValue = if (cardInfo.cardState == CardState.CLOSED) 0f else 180f,
        animationSpec = tween(500, easing = LinearEasing),
        label = ""
    )

    val animateFront by animateFloatAsState(
        targetValue = if (cardInfo.cardState == CardState.CLOSED) 1f else 0f,
        animationSpec = snap(delayMillis = 250),
        label = ""
    )

    val animateBack by animateFloatAsState(
        targetValue = if (cardInfo.cardState != CardState.CLOSED) 1f else 0f,
        animationSpec = snap(delayMillis = 250),
        label = ""
    )


    val animateBorderColor by animateColorAsState(
        targetValue = if (cardInfo.cardState != CardState.CLOSED && cardInfo.isSpy) AppTheme.colors.secondary else AppTheme.colors.primary,
        animationSpec = snap(delayMillis = 250),
        label = ""
    )

    Card(
        modifier = modifier
            .offset(x = offset, y = -offset)
            .graphicsLayer {
                this.rotationY = rotationY
                this.rotationZ = rotationZ
                cameraDistance = 8 * density
            },
        border = BorderStroke(
            width = 2.dp,
            color = animateBorderColor
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.primary
        ),
        onClick = onCardClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = AppTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            CardClosed(
                modifier = Modifier
                    .padding(16.dp)
                    .graphicsLayer {
                        alpha = animateFront
                    },
                playerNumber = playerNumber
            )
            when {
                (cardInfo.cardState == CardState.OPENED || cardInfo.cardState == CardState.SKIPPED) && !cardInfo.isSpy ->
                    CardOpenedLocal(
                        modifier = Modifier
                            .padding(16.dp)
                            .graphicsLayer {
                                this.rotationY = 180f
                                alpha = animateBack
                            },
                        location = cardInfo.location
                    )

                else ->
                    CardOpenedSpy(
                        modifier = Modifier
                            .padding(16.dp)
                            .graphicsLayer {
                                this.rotationY = 180f
                                alpha = animateBack
                            },
                    )
            }
        }
    }
}


@Composable
private fun CardClosed(
    modifier: Modifier = Modifier,
    playerNumber: Int,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${stringResource(resource = Res.string.player)} $playerNumber",
            style = AppTheme.types.h48
        )
        Text(
            text = stringResource(resource = Res.string.tap_to_see_you_role),
            style = AppTheme.types.h24,
            textAlign = TextAlign.Center
        )
    }

}

@Composable
private fun CardOpenedLocal(
    modifier: Modifier = Modifier,
    location: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.width(104.dp),
            painter = painterResource(resource = Res.drawable.ic_location),
            contentDescription = null
        )
        Text(
            text = location,
            style = AppTheme.types.h48,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(resource = Res.string.you_local),
            style = AppTheme.types.h24,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CardOpenedSpy(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.width(208.dp),
            painter = painterResource(resource = Res.drawable.ic_hat),
            contentDescription = null,
            tint = AppTheme.colors.secondary
        )
        Text(
            text = stringResource(resource = Res.string.you_spy),
            style = AppTheme.types.h48,
            color = AppTheme.colors.secondary
        )
        Text(
            text = stringResource(resource = Res.string.you_spy_hint),
            style = AppTheme.types.h24,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.topBarHeight),
        contentAlignment = Alignment.CenterStart
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(resource = Res.drawable.ic_back),
                tint = AppTheme.colors.primary,
                contentDescription = null
            )
        }
    }
}