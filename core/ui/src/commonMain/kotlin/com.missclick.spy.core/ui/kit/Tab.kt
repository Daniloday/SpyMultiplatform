package com.missclick.spy.core.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.ui.theme.AppTheme

@Composable
fun Tab(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
) {
    Box(
        modifier = modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) AppTheme.colors.secondary else AppTheme.colors.primary
            )
    )
}