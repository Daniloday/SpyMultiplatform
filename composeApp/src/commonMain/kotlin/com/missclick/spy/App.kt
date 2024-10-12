package com.missclick.spy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import com.missclick.spy.core.advertising.BottomAds
import com.missclick.spy.core.data.DeviceRepo
import com.missclick.spy.core.domain.GetOptionsUseCase
import com.missclick.spy.core.navigation.NavGraph
import com.missclick.spy.core.ui.kit.AppDivider
import com.missclick.spy.core.ui.kit.buttons.PrimaryButton
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.core.ui.theme.SpyTheme
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App(
    getOptionsUseCase: GetOptionsUseCase = koinInject(),
    deviceRepo: DeviceRepo = koinInject(),
) {

    LaunchedEffect(Unit) {
        val options = getOptionsUseCase.invoke().first()
        deviceRepo.setLanguage(options.selectedLanguageCode)
    }


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