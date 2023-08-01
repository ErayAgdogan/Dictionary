package com.goander.dictionary.bookmark

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

private const val BOOKMARKED_WORDS_DESTINATION = "bookmarked_words_destination"


public fun NavGraphBuilder.bookmarkedWordsComposable(
    navController: NavHostController,
    navigateToSearchScreen: (String) -> Unit) {
    composable(BOOKMARKED_WORDS_DESTINATION) {
        BookmarkedWordsScreen(
            navigateToSearchScreen = navigateToSearchScreen,
            navigateToBack = navController::popBackStack,
            viewModel = hiltViewModel())
    }
}

public fun NavHostController.navigateToBookmarkedWords() {
    navigate(BOOKMARKED_WORDS_DESTINATION)
}