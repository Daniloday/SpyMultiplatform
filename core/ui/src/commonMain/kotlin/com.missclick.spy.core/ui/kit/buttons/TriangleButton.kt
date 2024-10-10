package com.missclick.spy.core.ui.kit.buttons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import androidx.graphics.shapes.CornerRounding
//import androidx.graphics.shapes.RoundedPolygon
//import androidx.graphics.shapes.toPath
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.ic_triangle
import org.jetbrains.compose.resources.painterResource


@Composable
fun TriangleButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    enabledColor: Color = AppTheme.colors.secondary,
    disabledColor: Color = AppTheme.colors.onSecondary,
) {
    IconButton(
        modifier = modifier,
        enabled = isEnabled,
        onClick = onClick
    ){
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
            ,
            painter = painterResource(Res.drawable.ic_triangle),
            contentDescription = "",
            tint = if (isEnabled) enabledColor else disabledColor
        )
    }
}

