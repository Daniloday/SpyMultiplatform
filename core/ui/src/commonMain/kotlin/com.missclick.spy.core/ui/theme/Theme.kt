package com.missclick.spy.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


@Composable
fun SpyTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColorScheme provides spyDarkColorPalette,
        LocalTypography provides spyTypography,
        LocalShapes provides spyShapes,
        LocalDimens provides spyDimens,
        content = content,
    )
}

object AppTheme {

    val colors: SpyColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val types: SpyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: SpyShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val dimens: SpyDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current

}