package com.missclick.spy.feature.game_options

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

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

    val activity = LocalContext.current as Activity

    GameOptionsRouteShared(
        modifier = modifier,
        onGuideClick = onGuideClick,
        onPremiumClick = onPremiumClick,
        onStartClick = onStartClick,
        onSettingsClick = onSettingsClick,
        onSelectSetClick = onSelectSetClick,
        vm = vm,
        rewardedAdManager = koinInject { parametersOf(activity) }
    )

}