package com.goander.dictionary

import android.provider.ContactsContract.Directory
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goander.dictionary.DictionaryNav.SEARCH_DESTINATION
import com.goander.dictionary.DictionaryNav.SETTINGS_DESTINATION
import com.goander.dictionary.DictionaryNav.START_DESTINATION
import com.goander.dictionary.search.SearchScreen
import com.goander.dictionary.settings.SettingScreen
import com.goander.dictionary.ui.start.StartScreen

private object DictionaryNav {
    public const val START_DESTINATION = "start_destinations"
    public const val SEARCH_DESTINATION = "search_destinations"
    public const val SETTINGS_DESTINATION = "settings_destinations"
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
        composable(START_DESTINATION) {

            StartScreen(

                navigateToSearch = { navController.navigate(SEARCH_DESTINATION) },
                navigateToSettings = { navController.navigate(SETTINGS_DESTINATION) }
            )
        }
        composable(SETTINGS_DESTINATION) {
            SettingScreen(viewModel = hiltViewModel())
        }
        composable(SEARCH_DESTINATION) {
            SearchScreen(searchViewModel = hiltViewModel())
        }

    }
}