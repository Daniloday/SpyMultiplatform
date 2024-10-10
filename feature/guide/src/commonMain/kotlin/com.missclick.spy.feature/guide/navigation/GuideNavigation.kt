package com.missclick.spy.feature.guide.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.missclick.spy.feature.guide.GuideRoute


const val GUIDE_ROUTE = "guide"

fun NavGraphBuilder.guideScreen(
    onBackClick: () -> Unit
) {

    composable(
        route = GUIDE_ROUTE,
    ) {
        GuideRoute(
            onBackClick = onBackClick
        )
    }

}

fun NavController.navigateToGuide() {
    navigate(GUIDE_ROUTE)
}