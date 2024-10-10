package com.missclick.spy.core.ui.kit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.ui.theme.AppTheme


@Composable
fun FrameText(
    modifier: Modifier = Modifier,
    text: String,
) {

    Card(
        modifier = modifier
            .height(36.dp)
            .defaultMinSize(minWidth = 64.dp),
        shape = AppTheme.shapes.frame,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.primary
        ),
        border = BorderStroke(width = 1.dp, color = AppTheme.colors.primary)
    ) {
        Box(modifier = modifier.defaultMinSize(minWidth = 64.dp)) {
            Text(
                text = text,
                style = AppTheme.types.h28,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp)
            )
        }
    }
}