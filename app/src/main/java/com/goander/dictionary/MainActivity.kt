package com.goander.dictionary

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goander.dictionary.design.DictionaryIcon
import com.goander.dictionary.search.navigateToSearchScreen
import com.goander.dictionary.ui.theme.DictionaryTheme
import com.goander.dictionary.ui.theme.Silver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  WindowCompat.setDecorFitsSystemWindows(window, false)
      //  window.statusBarColor = Color(0x00000000).toArgb()

        setContent {


            DictionaryTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController: NavHostController = rememberNavController()
                    DirectoryNav(
                        navController = navController
                    )
                    val word = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT)
                    if (!word.isNullOrBlank())
                        navController.navigateToSearchScreen(word = word)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun StartScreen(
    viewModel: MainViewModel = viewModel(),
    navigateToSearch: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
    navigateToBookmarkedWords: () -> Unit = {}
) {

    Scaffold(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
        ,
        topBar = {
            TopAppBar (
                navigationIcon = {
                    Image(
                        modifier = Modifier
                            .size(72.dp),
                        painter = painterResource(id = R.drawable.ic_logo_24),
                        contentDescription = ""
                    )
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = { navigateToBookmarkedWords() }) {
                        Icon(
                            modifier = Modifier

                                .size(32.dp),
                            painter = painterResource(id = DictionaryIcon.BookMark),
                            contentDescription = "",
                        )
                    }

                    IconButton(
                        onClick = { navigateToSettings() }) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            painter = painterResource(id = R.drawable.ic_round_settings_24),
                            contentDescription = "",
                        )
                    }
                }
            )

        }
    ) {
        ConstraintLayout(modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .padding(it)) {
            val (textStart, textSearching, cardSearch) = createRefs()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .constrainAs(cardSearch) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(100),
                onClick = { navigateToSearch() },
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 8.dp)
                        .width(36.dp)
                        .align(Alignment.End),
                    painter = painterResource(id = R.drawable.ic_round_search_24),
                    contentDescription = stringResource(id = R.string.search),
                )

            }
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .constrainAs(textSearching) {
                        start.linkTo(parent.start)
                        bottom.linkTo(cardSearch.top)
                    },
                text = stringResource(id = R.string.searching),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displaySmall
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .constrainAs(textStart) {
                        start.linkTo(parent.start)
                        bottom.linkTo(textSearching.top)

                    },
                text = stringResource(id = R.string.Start),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}


@Preview
@Composable
private fun StartPagePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        StartScreen(viewModel = MainViewModel())
    }
}


private const val START_DESTINATION = "start_destinations"

public fun NavGraphBuilder.composableStartScreen(
    navigateToSearch: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToBookmarkedWords: () -> Unit

) {
    composable(START_DESTINATION) {

        StartScreen(
            navigateToSearch = navigateToSearch,
            navigateToSettings = navigateToSettings,
            navigateToBookmarkedWords = navigateToBookmarkedWords
        )
    }
}
