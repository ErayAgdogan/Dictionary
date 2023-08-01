package com.goander.dictionary.settings

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

private const val SETTINGS_DESTINATION = "settings_destinations"


public fun NavGraphBuilder.composableSetting(navController: NavHostController) {
    composable(SETTINGS_DESTINATION) {
        SettingScreen(
            navigateToBack = navController::popBackStack,
            viewModel = hiltViewModel()
        )
    }
}

public fun NavHostController.navigateToSettingScreen() {
    navigate(SETTINGS_DESTINATION)
}