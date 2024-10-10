package com.missclick.spy.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class SpyColorScheme(
    val primary: Color,
    val secondary: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val background: Brush,
)


val spyDarkColorPalette = SpyColorScheme(
    primary = Color(0xFFFFFBE7),
    secondary = Color(0xFFF07665),
    onSecondary = Color(0x54F07665),
    onPrimary = Color(0x80FFFBE7),
    background = Brush.verticalGradient(
        listOf(
            Color(0xFF1E1C77),
            Color(0xFF171A37),
        )
    )
)


val LocalColorScheme = staticCompositionLocalOf<SpyColorScheme> {
    error("No colors provided")
}
