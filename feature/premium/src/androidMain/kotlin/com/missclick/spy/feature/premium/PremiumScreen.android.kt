package com.missclick.spy.feature.premium

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
internal actual fun PremiumRoute(
    onBackClick: () -> Unit,
    modifier: Modifier,
    vm: PremiumViewModel,
) {

    val activity = LocalContext.current as Activity

    PremiumRouteShared(
        onBackClick = onBackClick,
        modifier = modifier,
        vm = vm,
        purchaseManager = koinInject { parametersOf(activity) }
    )
}