package com.missclick.spy.feature.premium

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.ui.kit.AppDivider
import com.missclick.spy.core.ui.kit.TopBar
import com.missclick.spy.core.ui.kit.buttons.PrimaryButton
import com.missclick.spy.core.ui.kit.buttons.SecondaryButton
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.all_future_features
import com.missclick.spy.resources.all_future_sets
import com.missclick.spy.resources.buy_premium
import com.missclick.spy.resources.enjoy_game
import com.missclick.spy.resources.forever
import com.missclick.spy.resources.ic_back
import com.missclick.spy.resources.no_ads
import com.missclick.spy.resources.play
import com.missclick.spy.resources.premium_sets
import com.missclick.spy.resources.restore_purchases
import com.missclick.spy.resources.spy_premium
import com.missclick.spy.resources.thanks_for_purchase
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun PremiumRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    vm: PremiumViewModel = koinViewModel(),
) {

    val viewState = vm.viewState.collectAsState()

    PremiumScreen(
        onBackClick = onBackClick,
        modifier = modifier,
        onBuy = vm::onBuy,
        onRestore = vm::onRestore,
        viewState = viewState.value,
    )
}

@Composable
private fun PremiumScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onBuy: () -> Unit,
    onRestore: () -> Unit,
    viewState: PremiumViewState,
) {
    Column(
        modifier = modifier
    ) {
        TopBarPremium(onBackClick = onBackClick)
        when (viewState) {
            is PremiumViewState.Loading -> Unit
            is PremiumViewState.NoPremium -> BuyPremiumContent(onBuy = onBuy, onRestore = onRestore)
            is PremiumViewState.Premium -> ThanYouContent(
                onPlay = onBackClick
            )
        }

    }
}

@Composable
fun TopBarPremium(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.topBarHeight),
        contentAlignment = Alignment.CenterStart
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(resource = Res.drawable.ic_back),
                tint = AppTheme.colors.primary,
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(Res.string.spy_premium),
            textAlign = TextAlign.Center,
            style = AppTheme.types.h30,
            color = AppTheme.colors.primary
        )
        AppDivider(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun ColumnScope.ThanYouContent(
    onPlay: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(16.dp).weight(1f).fillMaxWidth()
    ) {
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
                Text(
                    text = stringResource(Res.string.no_ads),
                    textAlign = TextAlign.Center,
                    style = AppTheme.types.h30
                )
                Text(
                    text = stringResource(Res.string.premium_sets),
                    textAlign = TextAlign.Center,
                    style = AppTheme.types.h30
                )
                Text(
                    text = stringResource(Res.string.all_future_sets),
                    textAlign = TextAlign.Center,
                    style = AppTheme.types.h30
                )
                Text(
                    text = stringResource(Res.string.all_future_features),
                    textAlign = TextAlign.Center,
                    style = AppTheme.types.h30
                )
                Text(
                    text = stringResource(Res.string.forever),
                    textAlign = TextAlign.Center,
                    style = AppTheme.types.h30
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(Res.string.thanks_for_purchase),
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    style = AppTheme.types.h30
                )
                Text(
                    text = stringResource(Res.string.enjoy_game),
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    style = AppTheme.types.h30
                )
            }
        }
    }
    PrimaryButton(
        modifier = Modifier.padding(bottom = 16.dp),
        text = stringResource(Res.string.play),
        onClick = onPlay
    )
}

@Composable
private fun ColumnScope.BuyPremiumContent(
    onBuy: () -> Unit,
    onRestore: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(16.dp).weight(1f).fillMaxWidth()
    ) {
        BuyPremiumOptionsCard()
    }
    SecondaryButton(
        Modifier.padding(bottom = 16.dp),
        text = stringResource(Res.string.restore_purchases),
        onClick = onRestore
    )
    PrimaryButton(
        modifier = Modifier.padding(bottom = 16.dp),
        text = stringResource(Res.string.buy_premium),
        onClick = onBuy
    )
}

@Composable
private fun BuyPremiumOptionsCard() {
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
            Text(
                text = stringResource(Res.string.no_ads),
                textAlign = TextAlign.Center,
                style = AppTheme.types.h30
            )
            Text(
                text = stringResource(Res.string.premium_sets),
                textAlign = TextAlign.Center,
                style = AppTheme.types.h30
            )
            Text(
                text = stringResource(Res.string.all_future_sets),
                textAlign = TextAlign.Center,
                style = AppTheme.types.h30
            )
            Text(
                text = stringResource(Res.string.all_future_features),
                textAlign = TextAlign.Center,
                style = AppTheme.types.h30
            )
            Text(
                text = stringResource(Res.string.forever),
                textAlign = TextAlign.Center,
                style = AppTheme.types.h30
            )
            Spacer(modifier = Modifier.height(18.dp))
            Row {
                Text(
                    text = "2.00$",
                    fontWeight = FontWeight.ExtraBold,
                    textDecoration = TextDecoration.LineThrough,
                    textAlign = TextAlign.Center,
                    style = AppTheme.types.h42
                )
                Text(
                    text = " / 0.99$",
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    style = AppTheme.types.h42
                )
            }
        }
    }
}