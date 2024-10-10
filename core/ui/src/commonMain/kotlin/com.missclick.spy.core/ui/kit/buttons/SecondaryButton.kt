package com.missclick.spy.core.ui.kit.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.ui.theme.AppTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.secondary
    )
) {
    Button(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(AppTheme.dimens.secondaryButtonHeight),
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        border = BorderStroke(2.dp, color = colors.contentColor),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = text,
            style = AppTheme.types.h30
        )
    }
}