package com.missclick.spy.feature.guide.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.savedstate.read
import com.missclick.spy.feature.guide.GuideRoute


const val GUIDE_ROUTE = "guide"
const val OPEN_HARD_MODE = "openHardMode"

fun NavGraphBuilder.guideScreen(
    onBackClick: () -> Unit
) {

    composable(
        route = "$GUIDE_ROUTE/{$OPEN_HARD_MODE}",
        arguments = listOf(navArgument(OPEN_HARD_MODE) { type = NavType.BoolType }),
    ) { entry ->

        val openHardMode =
            entry.arguments?.read { getBooleanOrNull(OPEN_HARD_MODE) } ?: false

        GuideRoute(
            onBackClick = onBackClick,
            openHardMode = openHardMode
        )
    }

}

fun NavController.navigateToGuide(openHardMode : Boolean) {
    navigate("$GUIDE_ROUTE/$openHardMode")
}