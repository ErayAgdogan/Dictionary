package com.goander.dictionary

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.goander.dictionary.DictionaryNav.START_DESTINATION
import com.goander.dictionary.bookmark.bookmarkedWordsComposable
import com.goander.dictionary.bookmark.navigateToBookmarkedWords
import com.goander.dictionary.search.composableSearchScreen
import com.goander.dictionary.search.navigateToSearchScreen
import com.goander.dictionary.settings.composableSetting
import com.goander.dictionary.settings.navigateToSettingScreen


private object DictionaryNav {
    public const val START_DESTINATION = "start_destinations"
}

@Composable
public fun DirectoryNav(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = START_DESTINATION
    ) {

        composableStartScreen(
            navigateToSearch = { navController.navigateToSearchScreen() },
            navigateToSettings = { navController.navigateToSettingScreen()},
            navigateToBookmarkedWords = { navController.navigateToBookmarkedWords() }
        )

        composableSetting(navController = navController)

        composableSearchScreen(navController = navController)

        bookmarkedWordsComposable(
            navController = navController,
            navigateToSearchScreen = { bookmarkedWord ->
            navController.navigateToSearchScreen(bookmarkedWord)
        })
    }
}