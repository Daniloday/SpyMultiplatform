package com.missclick.spy.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.missclick.spy.feature.collections.navigation.collectionsScreen
import com.missclick.spy.feature.collections.navigation.navigateBackToCollections
import com.missclick.spy.feature.collections.navigation.navigateToCollections
import com.missclick.spy.feature.game.navigation.gameScreen
import com.missclick.spy.feature.game.navigation.navigateToGame
import com.missclick.spy.feature.game_options.navigation.gameOptionsScreen
import com.missclick.spy.feature.game_options.navigation.navigateBackToGameOptions
import com.missclick.spy.feature.game_options.navigation.navigateToGameOptions
import com.missclick.spy.feature.guide.navigation.guideScreen
import com.missclick.spy.feature.guide.navigation.navigateToGuide
import com.missclick.spy.feature.rules.navigation.RULES_ROUTE
import com.missclick.spy.feature.rules.navigation.rulesScreen
import com.missclick.spy.feature.settings.navigation.navigateToSettings
import com.missclick.spy.feature.settings.navigation.settingsScreen
import com.missclick.spy.feature.words.navigation.navigateToWords
import com.missclick.spy.feature.words.navigation.wordsScreen
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.app_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
) {

    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = RULES_ROUTE
    ) {
        rulesScreen(onSkipClick = navController::navigateToGameOptions)
        gameOptionsScreen(
            onStartClick = navController::navigateToGame,
            onGuideClick = navController::navigateToGuide,
            onSettingsClick = navController::navigateToSettings,
            onSelectSetClick = navController::navigateToCollections,
        )
        gameScreen(onBackClick = navController::navigateBackToGameOptions)
        guideScreen(onBackClick = navController::navigateBackToGameOptions)
        settingsScreen(
            onBackClick = navController::navigateBackToGameOptions,
        )
        collectionsScreen(
            onBackClick = navController::navigateBackToGameOptions,
            onCollectionClick = navController::navigateToWords,
        )
        wordsScreen(
            onBackClick = navController::navigateBackToCollections,
            onSelectCollection = navController::navigateBackToGameOptions,
        )

    }

}