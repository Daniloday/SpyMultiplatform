package com.missclick.spy.feature.sets.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.missclick.spy.feature.sets.CollectionsRoute


const val COLLECTIONS_ROUTE = "Collections"

fun NavGraphBuilder.collectionsScreen(
    onBackClick: () -> Unit,
    onCollectionClick: (String) -> Unit,
) {

    composable(
        route = COLLECTIONS_ROUTE,
    ) {
        CollectionsRoute(
            onBackClick = onBackClick,
            onCollectionClick = onCollectionClick
        )
    }

}

fun NavController.navigateToCollections() {
    navigate(COLLECTIONS_ROUTE)
}

fun NavController.navigateBackToCollections() {
    popBackStack(route = COLLECTIONS_ROUTE, inclusive = false)
}