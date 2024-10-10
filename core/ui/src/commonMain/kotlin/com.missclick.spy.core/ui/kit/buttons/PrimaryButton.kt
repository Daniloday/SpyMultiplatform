package com.missclick.spy.core.ui.kit.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.missclick.spy.core.ui.theme.AppTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = AppTheme.colors.secondary,
        disabledContainerColor = AppTheme.colors.onSecondary
    ),
) {
    Button(
        modifier = modifier
            .padding(horizontal = AppTheme.dimens.horizontalPadding)
            .fillMaxWidth()
            .height(AppTheme.dimens.primaryButtonHeight),
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        shape = AppTheme.shapes.button,
    ) {
        Text(text = text, style = AppTheme.types.h30)
    }
}



