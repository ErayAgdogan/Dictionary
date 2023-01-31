package com.goander.dictionary.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goander.dictionary.R
import com.goander.dictionary.ui.theme.DictionaryTheme
import com.goander.dictionary.utility.NetworkConnectivityObserver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel()
) {

    var text  by rememberSaveable { mutableStateOf("") }
    val dictionaryResponse by searchViewModel.dictionaryFlow.collectAsState()
    val networkConnectivity by searchViewModel.networkConnectivity
        .collectAsState(initial = NetworkConnectivityObserver.Status.Unavailable
    )

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {

                ConnectionInformer(networkConnectivity)

                Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = RectangleShape
                ) {
                    TextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                            ),
                            value = text,
                            onValueChange = { text = it },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                searchViewModel.search(word = text)
                            })
                    )
                }
            }

        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            item {

                Text(
                    text = dictionaryResponse?.word ?: "",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(dictionaryResponse?.meanings?: ArrayList()) {
                Text(
                    text = it.definitions
                        .map{ it.definition }
                        .joinToString(separator = "\n",)
                )
            }

        }
    }
}

@Composable
public fun ConnectionInformer(networkConnectivity: NetworkConnectivityObserver.Status) {
    AnimatedVisibility(visible = networkConnectivity != NetworkConnectivityObserver.Status.Available) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .animateContentSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_signal_wifi_connected_no_internet_4_24),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.wrapContentSize(),
                text = "connection unavailable",
            )
        }
    }


}

@Composable
@Preview
public fun SearchScreenPreview() {
    DictionaryTheme {
        //SearchScreen(SearchViewModel(SavedStateHandle()))
    }
}