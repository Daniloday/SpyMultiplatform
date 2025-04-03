package com.missclick.spy.feature.premium.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val PREMIUM_ROUTE = "premium"

fun NavGraphBuilder.premiumScreen(
    onBackClick: () -> Unit
) {

    composable(
        route = PREMIUM_ROUTE,
    ) {

    }

}

fun NavController.navigateToPremium() {
    navigate(PREMIUM_ROUTE)
}