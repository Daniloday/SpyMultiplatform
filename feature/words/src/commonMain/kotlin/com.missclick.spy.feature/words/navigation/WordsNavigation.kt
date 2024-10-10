package com.missclick.spy.feature.words.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.missclick.spy.feature.words.WordsRoute

private const val COLLECTION_NAME = "collectionName"
const val WORDS_NAV_ROUTE = "wordsNavRoute"

data class WordsNavRoute(
    val selectedCollectionName: String
)

fun NavGraphBuilder.wordsScreen(
    onBackClick: () -> Unit,
    onSelectCollection: () -> Unit,
) {

    composable(
        route = "$WORDS_NAV_ROUTE/{$COLLECTION_NAME}",
        arguments = listOf(
            navArgument(COLLECTION_NAME) { type = NavType.StringType },
        ),
    ) { navBackStackEntry ->
        val selectedCollectionName = remember {
            navBackStackEntry.arguments?.getString(COLLECTION_NAME) ?: ""
        }
        WordsRoute(
            onBackClick = onBackClick,
            onSelectCollection = onSelectCollection,
            selectedCollectionName = selectedCollectionName
        )
    }

}

fun NavController.navigateToWords(selectedCollectionName: String) {
    navigate("$WORDS_NAV_ROUTE/$selectedCollectionName")
}