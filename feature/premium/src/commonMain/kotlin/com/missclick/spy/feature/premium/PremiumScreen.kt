package com.missclick.spy.feature.premium

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.ui.kit.TopBar
import com.missclick.spy.core.ui.kit.buttons.PrimaryButton
import com.missclick.spy.core.ui.kit.buttons.SecondaryButton
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.all_future_features
import com.missclick.spy.resources.all_future_sets
import com.missclick.spy.resources.buy_premium
import com.missclick.spy.resources.forever
import com.missclick.spy.resources.no_ads
import com.missclick.spy.resources.premium_sets
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PremiumRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    PremiumScreen(
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun PremiumScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        TopBar(onBackClick = onBackClick)
        PremiumContent(onBuy = {}, onRestore = {})
    }
}

@Composable
private fun ColumnScope.PremiumContent(
    onBuy: () -> Unit,
    onRestore: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(16.dp).weight(1f).fillMaxWidth()
    ) {
        PremiumOptionsCard()
    }
    PrimaryButton(modifier = Modifier.padding(bottom = 16.dp), text = stringResource(Res.string.buy_premium), onClick = onBuy)
//    SecondaryButton(text = "Restore", onClick = onRestore)
}

@Composable
private fun PremiumOptionsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, AppTheme.colors.primary),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.primary,
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = stringResource(Res.string.no_ads), textAlign = TextAlign.Center, style = AppTheme.types.h30)
            Text(text = stringResource(Res.string.premium_sets),textAlign = TextAlign.Center, style = AppTheme.types.h30)
            Text(text = stringResource(Res.string.all_future_sets),textAlign = TextAlign.Center, style = AppTheme.types.h30)
            Text(text = stringResource(Res.string.all_future_features),textAlign = TextAlign.Center, style = AppTheme.types.h30)
            Text(text = stringResource(Res.string.forever),textAlign = TextAlign.Center, style = AppTheme.types.h30)
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Text(text = "2.00$", fontWeight = FontWeight.ExtraBold, textDecoration = TextDecoration.LineThrough, textAlign = TextAlign.Center, style = AppTheme.types.h42)
                Text(text = " / 0.99$", fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center, style = AppTheme.types.h42)
            }
        }
    }
}