package com.missclick.spy.core.ui.theme

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class SpyShapes(
    val button: Shape,
    val frame: Shape,
    val rectangle: Shape,
    val triangle: Shape,
)

private val triangle: Shape = GenericShape { size, _ ->
    val path = Path().apply {
        moveTo(0f, 0f) // Левая верхняя вершина
        lineTo(size.width, size.height / 2f) // Правая центральная вершина
        lineTo(0f, size.height) // Левая нижняя вершина
        close() // Закрыть контур
    }
    addPath(path)
}

internal val spyShapes = SpyShapes(
    button = RoundedCornerShape(16.dp),
    frame = RoundedCornerShape(4.dp),
    rectangle = RoundedCornerShape(0.dp),
    triangle = triangle
)

internal val LocalShapes = staticCompositionLocalOf<SpyShapes> {
    error("No shapes provided")
}