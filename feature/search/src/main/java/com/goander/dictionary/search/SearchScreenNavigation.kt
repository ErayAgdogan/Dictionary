package com.goander.dictionary.search

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val SEARCH_DESTINATION = "search_destination"

public fun NavGraphBuilder.composableSearchScreen(
    navController: NavHostController
) {
    composable("$SEARCH_DESTINATION?$SEARCH_KEYWORD={$SEARCH_KEYWORD}",
        arguments = listOf(navArgument(SEARCH_KEYWORD) { defaultValue = ""})
        ) {
        SearchScreen(
            navigateToBack = navController::popBackStack,
            searchViewModel = hiltViewModel()
        )
    }
}

public fun NavController.navigateToSearchScreen(word: String = "") {
    navigate("$SEARCH_DESTINATION?$SEARCH_KEYWORD=${word}")
}