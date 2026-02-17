package com.missclick.spy.feature.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
internal actual fun GameRoute(
    modifier: Modifier,
    onBackClick: () -> Unit,
) {
    GameRouteShared(
        modifier = modifier,
        onBackClick = onBackClick,
        interstitialAdManager = koinInject(),
        rateUs = koinInject()
    )
}