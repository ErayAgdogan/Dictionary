package com.goander.dictionary

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.goander.dictionary.search.SearchScreen

import com.goander.dictionary.ui.theme.DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint

@Deprecated(message = "replace it")
private const val SEARCH_KEYWORD = "SEARCH_KEYWORD"

@AndroidEntryPoint
class ProcessTextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DictionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProcessTextNav()
                  }
            }
        }
    }
    @Composable
    private fun ProcessTextNav(
        navController: NavHostController = rememberNavController()
    ) {
        NavHost(
            navController = navController,
            startDestination = "searchScreen/{$SEARCH_KEYWORD}"
        ) {
            composable(
                route = "searchScreen/{$SEARCH_KEYWORD}",
                arguments = listOf(navArgument(SEARCH_KEYWORD) {
                     type = NavType.StringType
                     defaultValue = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT) ?: ""
                 } )
            ) {
                SearchScreen(searchViewModel = hiltViewModel())
            }
        }

    }
}

