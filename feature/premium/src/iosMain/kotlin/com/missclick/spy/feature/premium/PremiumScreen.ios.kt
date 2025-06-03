package com.missclick.spy.feature.premium

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
internal actual fun PremiumRoute(
    onBackClick: () -> Unit,
    modifier: Modifier,
    vm: PremiumViewModel,
) {
    PremiumRouteShared(
        onBackClick = onBackClick,
        modifier = modifier,
        vm = vm,
        purchaseManager = koinInject()
    )
}