package com.missclick.spy.feature.game_options

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
internal actual fun GameOptionsRoute(
    modifier: Modifier,
    onSettingsClick: () -> Unit,
    onGuideClick: (Boolean) -> Unit,
    onStartClick: () -> Unit,
    onSelectSetClick: () -> Unit,
    onPremiumClick: () -> Unit,
    vm: GameOptionsViewModel,
) {

    GameOptionsRouteShared(
        modifier = modifier,
        onGuideClick = onGuideClick,
        onPremiumClick = onPremiumClick,
        onStartClick = onStartClick,
        onSettingsClick = onSettingsClick,
        onSelectSetClick = onSelectSetClick,
        vm = vm,
        rewardedAdManager = koinInject()
    )

}