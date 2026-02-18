package com.missclick.spy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.missclick.spy.core.advertising.BottomAds
import com.missclick.spy.core.navigation.NavGraph
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.core.ui.theme.SpyTheme
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun App(
    vm: AppViewModel = koinViewModel()
) {

    val isPremium by vm.isPremium.collectAsState()
    val isShowPaywall by vm.isShowPaywall.collectAsState()

    val options = remember { PaywallOptions({ vm.closePaywall() }) }

    SpyTheme {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = AppTheme.colors.background
                    )
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavGraph(
                    modifier = Modifier.weight(1f),
                    onShowPaywall = vm::showPaywall
                )
                if (!isPremium) {
                    BottomAds()
                }
            }

            if (isShowPaywall) {
                Paywall(options)
            }
        }
    }
}

