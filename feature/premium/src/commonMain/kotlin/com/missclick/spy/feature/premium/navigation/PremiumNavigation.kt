package com.missclick.spy.feature.premium.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.missclick.spy.feature.premium.PremiumRoute

const val PREMIUM_ROUTE = "premium"

fun NavGraphBuilder.premiumScreen(
    onBackClick: () -> Unit
) {

    composable(
        route = PREMIUM_ROUTE,
    ) {
        PremiumRoute(onBackClick = onBackClick)
    }

}

fun NavController.navigateToPremium() {
    navigate(PREMIUM_ROUTE)
}