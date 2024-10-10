package com.missclick.spy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import com.missclick.spy.core.domain.GetOptionsUseCase
import com.missclick.spy.core.navigation.NavGraph
import com.missclick.spy.core.ui.kit.AppDivider
import com.missclick.spy.core.ui.kit.buttons.PrimaryButton
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.core.ui.theme.SpyTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun App(
    onChangeLanguage: (String) -> Unit,
    getOptionsUseCase: GetOptionsUseCase = koinInject()
) {

    LaunchedEffect(Unit) {
        getOptionsUseCase().collect { option ->
            onChangeLanguage(option.selectedLanguageCode)
        }
    }

    SpyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = AppTheme.colors.background
                )
        ) {
            NavGraph(modifier = Modifier.weight(1f))
//            BottomAds()
        }
    }
}