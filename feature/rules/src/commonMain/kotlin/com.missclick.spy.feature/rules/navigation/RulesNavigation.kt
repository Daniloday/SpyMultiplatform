package com.missclick.spy.feature.rules.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.missclick.spy.feature.rules.RulesRoute

const val RULES_ROUTE = "rulesRoute"

fun NavGraphBuilder.rulesScreen(
    onSkipClick: () -> Unit
) {

    composable(
        route = RULES_ROUTE,
    ) {
       RulesRoute(onSkipClick = onSkipClick)
    }

}