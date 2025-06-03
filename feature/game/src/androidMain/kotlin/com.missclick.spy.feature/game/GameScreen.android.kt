package com.missclick.spy.feature.game

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
internal actual fun GameRoute(
    modifier: Modifier,
    onBackClick: () -> Unit,
) {

    val activity = LocalContext.current as Activity

    GameRouteShared(
        modifier = modifier,
        onBackClick = onBackClick,
        interstitialAdManager = koinInject { parametersOf(activity) },
        purchaseManager = koinInject { parametersOf(activity) },
    )
}