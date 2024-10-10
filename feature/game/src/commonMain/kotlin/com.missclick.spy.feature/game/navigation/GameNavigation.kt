package com.missclick.spy.feature.game.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.missclick.spy.feature.game.GameRoute

const val GAME_ROUTE = "game"

fun NavGraphBuilder.gameScreen(
    onBackClick: () -> Unit
) {

    composable(
        route = GAME_ROUTE,
    ) {
        GameRoute(
            onBackClick = onBackClick
        )
    }

}

fun NavController.navigateToGame() {
    navigate(GAME_ROUTE)
}