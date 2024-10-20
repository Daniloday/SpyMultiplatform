package com.missclick.spy

import androidx.compose.foundation.background
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

@Composable
internal fun App() {

    SpyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = AppTheme.colors.background
                )
                .windowInsetsPadding(WindowInsets.safeDrawing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavGraph(modifier = Modifier.weight(1f))
            BottomAds()
        }
    }
}