package com.missclick.spy.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SpyDimens(
    val primaryButtonHeight: Dp,
    val secondaryButtonHeight: Dp,
    val horizontalPadding: Dp,
    val topBarHeight: Dp,
)

val spyDimens = SpyDimens(
    primaryButtonHeight = 56.dp,
    secondaryButtonHeight = 56.dp,
    horizontalPadding = 16.dp,
    topBarHeight = 56.dp,
)

val LocalDimens = staticCompositionLocalOf<SpyDimens> {
    error("No dimens provided")
}
