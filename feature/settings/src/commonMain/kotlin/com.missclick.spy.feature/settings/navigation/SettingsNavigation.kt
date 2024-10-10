package com.missclick.spy.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.missclick.spy.feature.settings.SettingsRoute


const val SETTINGS_ROUTE = "settings"

fun NavGraphBuilder.settingsScreen(
    onBackClick: () -> Unit,
) {

    composable(
        route = SETTINGS_ROUTE,
    ) {
        SettingsRoute(
            onBackClick = onBackClick,
        )
    }

}

fun NavController.navigateToSettings() {
    navigate(SETTINGS_ROUTE)
}