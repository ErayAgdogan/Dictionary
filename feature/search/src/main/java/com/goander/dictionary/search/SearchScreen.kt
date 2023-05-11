package com.goander.dictionary.search

import android.app.Activity.RESULT_OK
import android.app.PendingIntent
import android.content.Intent
import android.media.MediaPlayer
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goander.dictionary.connectivity.NetworkConnectivityObserver
import com.goander.dictionary.design.DictionaryIcon
import com.goander.dictionary.design.DictionaryString
import com.goander.dictionary.model.Definition
import com.goander.dictionary.model.Phonetic
import com.goander.dictionary.ui.NoResult
import com.goander.dictionary.ui.SearchResultItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.List
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
public fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel()
) {

    val searchUIState by searchViewModel.searchUIState.collectAsStateWithLifecycle()
    val preSearchText by searchViewModel.preSearchText.collectAsStateWithLifecycle()
    val isWordSaved by searchViewModel.isWordSaved.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val message = stringResource(id = DictionaryString.Example)

    val networkConnectivity by searchViewModel.networkConnectivity
        .collectAsState(NetworkConnectivityObserver.Status.Unavailable)

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    // https://stackoverflow.com/a/70424733/15038120
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
    )
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH.toLanguageTag())
    val context = LocalContext.current

    val pendIntent = PendingIntent.getActivity(context, 0, intent, 0)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (it.resultCode != RESULT_OK) {
            return@rememberLauncherForActivityResult
        }

        //GET TEXT ARRAY FROM VOICE INTENT
        val result = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

        if (result !== null) {
            searchViewModel.search(result[0] ?: "")
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                        leadingIcon = {
                            IconButton(
                                modifier = Modifier,
                                onClick = {

                            }) {
                                Icon(
                                    modifier = Modifier
                                        .size(32.dp),
                                    painter = painterResource(id = DictionaryIcon.Back),
                                    contentDescription = stringResource(id = DictionaryString.BackToMainScreen)
                                )
                            }

                        },
                        trailingIcon = {
                            IconButton(
                                modifier = Modifier,
                                onClick = {
                                launcher.launch(
                                    IntentSenderRequest
                                        .Builder(pendIntent)
                                        .build()
                                )
                            }) {

                                Icon(
                                    modifier = Modifier
                                        .size(32.dp),
                                    painter = painterResource(id = DictionaryIcon.Mic),
                                    contentDescription = ""
                                )
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .focusRequester(focusRequester)
                            ,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        value = preSearchText,
                        onValueChange = searchViewModel::setPreSearchText,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            preSearchText.let(searchViewModel::search)
                        })
                    )
                }
            }

        }
    ) {
        when(val localSearchUIState = searchUIState) {
            is SearchUIState.Loading -> Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is SearchUIState.NoResult -> NoResult(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
            is SearchUIState.ShowSearchHistory -> LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ){
                items(localSearchUIState.searchHistoryList, key = {it.id}) { searchHistory ->
                    SearchResultItem(
                        modifier = Modifier
                        .clickable { searchViewModel.search(searchHistory.search) }
                        .animateItemPlacement(),
                        searchResult = searchHistory.search,
                        searchedText = localSearchUIState.searchedText,
                        ignoreCaseOnHighLight = true,
                        onDelete = { searchViewModel.deleteSearch(searchHistory.id) }
                    )
                }
            }

            is SearchUIState.Result -> SelectionContainer {
                LazyColumn(
                    modifier = Modifier
                        .padding(it),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)

                ) {

                    itemWord(
                        word = localSearchUIState.dictionary.word,
                        isWordSaved = isWordSaved,
                        onSaveWord = {
                            scope.launch {
                                searchViewModel.saveDictionary(localSearchUIState.dictionary)
                            }
                    })

                    itemsPhonetics(localSearchUIState.dictionary.phonetics) { source ->
                        try {
                            MediaPlayer().run {
                                reset()
                                setDataSource(source)
                                prepare()
                                start()
                            }
                        }catch (e:Exception) {

                        }
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                    localSearchUIState.dictionary.meanings.forEach { meaning->
                        itemPartOfSpeech(meaning.partOfSpeech)
                        itemsDefinitions(meaning.definitions)
                    }

                }
            }
        }
    }
}



public fun LazyListScope.itemWord(word: String, isWordSaved: Boolean, onSaveWord: () -> Unit) =
    item {
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = word,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black
            )

            IconButton(onClick = {
                onSaveWord()

            }) {
                Icon(
                    painter = painterResource(id =
                    if (isWordSaved) DictionaryIcon.BookMark
                    else DictionaryIcon.BookMarkBorder
                    ),
                    contentDescription = stringResource(id = DictionaryString.SaveTheWord)
                )
            }

        }
    }

public inline fun LazyListScope.itemsPhonetics(
    phonetics: List<Phonetic>,
    crossinline onMediaPlay: (source: String) -> Unit
) =
    items(phonetics) { phonetic ->
        Row (horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(
                text = phonetic.text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Light
            )

            if (phonetic.audio.isNotBlank())
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {

                        onMediaPlay(phonetic.audio)


                    }) {
                    Icon(
                        painter = painterResource(id = DictionaryIcon.VolumeUp),
                        contentDescription = "",
                    )
                }


        }

    }

public fun LazyListScope.itemPartOfSpeech(partOfSpeech: String) =
    item {
        Text(
            text = partOfSpeech,
            color = MaterialTheme.colorScheme.primary
        )
    }

public fun LazyListScope.itemsDefinitions(definitions: List<Definition>) =
    itemsIndexed(definitions) { index, definition ->
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = DictionaryString.Index, index + 1))
                }
                append(definition.definition)

                if (definition.example.isNotBlank()) {
                    append("\n")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(stringResource(id = DictionaryString.Example))
                    }
                    append("\"${definition.example}\"")
                }
            }
        )
    }



@Composable
public fun ConnectionInformer(networkConnectivity: NetworkConnectivityObserver.Status) {
    var shouldBannerVisible by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit, block = {
        delay(200.milliseconds)
        shouldBannerVisible = true
    })
    if (shouldBannerVisible)
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
                    painter = painterResource(id = DictionaryIcon.NoInternet),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = "connection unavailable",
                )
            }
        }



}

