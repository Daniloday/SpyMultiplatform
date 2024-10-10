package com.missclick.spy.feature.game_options.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.missclick.spy.feature.game_options.GameOptionsRoute

const val GAME_OPTIONS_ROUTE = "gameOptions"

fun NavGraphBuilder.gameOptionsScreen(
    onStartClick: () -> Unit,
    onGuideClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onSelectSetClick: () -> Unit,
) {

    composable(
        route = GAME_OPTIONS_ROUTE,
    ) {
        GameOptionsRoute(
            onGuideClick = onGuideClick,
            onSettingsClick = onSettingsClick,
            onStartClick = onStartClick,
            onSelectSetClick = onSelectSetClick,
        )
    }

}

fun NavController.navigateToGameOptions() {
    navigate(GAME_OPTIONS_ROUTE)
}

fun NavController.navigateBackToGameOptions() {
    popBackStack(route = GAME_OPTIONS_ROUTE, inclusive = false)
}