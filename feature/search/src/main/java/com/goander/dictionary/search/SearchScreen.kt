package com.goander.dictionary.search

import android.media.MediaPlayer
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.goander.dictionary.connectivity.NetworkConnectivityObserver
import com.goander.dictionary.design.DictionaryIcon
import com.goander.dictionary.ui.ConnectionInformerBanner
import com.goander.dictionary.ui.NoResult
import com.goander.dictionary.ui.SearchResultItem
import com.goander.dictionary.ui.launcherSpeechToText
import kotlinx.coroutines.flow.Flow
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
public fun SearchScreen(
    navigateToBack: () -> Unit,
    searchViewModel: SearchViewModel = viewModel()
) {

    val preSearchText by searchViewModel.preSearchText.collectAsStateWithLifecycle()
    val isWordBookmarked by searchViewModel.isWordBookmarked.collectAsStateWithLifecycle()

    val showExamples by searchViewModel.showExamples.collectAsStateWithLifecycle()
    val showSynonyms by searchViewModel.showSynonyms.collectAsStateWithLifecycle()
    val showAntonyms by searchViewModel.showAntonyms.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val showSearchHistory by searchViewModel.showSearchHistory.collectAsStateWithLifecycle()
    val networkConnectivity by searchViewModel.networkConnectivity
        .collectAsStateWithLifecycle(NetworkConnectivityObserver.Status.Unavailable)

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        if (showSearchHistory)
            focusRequester.requestFocus()
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {

            TopSearchBar(
                isConnectionAvailable = networkConnectivity == NetworkConnectivityObserver.Status.Available,
                onSpeechToTextRequest = launcherSpeechToText(onTextReceived = searchViewModel::search),
                focusRequester = focusRequester,
                preSearchText = preSearchText,
                setPreSearchText = searchViewModel::setPreSearchText,
                isActive = showSearchHistory,
                onActiveChanged = searchViewModel::showSearchHistory,
                search = searchViewModel::search,
                navigateToBack = navigateToBack,
                searchHistoryContent = {
                    SearchHistory(
                        preSearchText = preSearchText,
                        searchHistoryPaging = searchViewModel.searchHistoryPaging,
                        onSelected = { search ->
                            searchViewModel.showSearchHistory(false)
                            searchViewModel.search(search)
                        },
                        onDelete = { id ->
                            searchViewModel.deleteSearch(id)
                        }
                    )
            })
        }
    ) {

        DictionaryComposable(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            dictionaryPage = searchViewModel.searchUIState,
            isWordBookmarked = isWordBookmarked,
            onBookmarkWord = searchViewModel::bookmarkWord,
            showExamples = showExamples,
            showSynonyms = showSynonyms,
            showAntonyms = showAntonyms
        )
    }


}

@Composable
private fun DictionaryComposable(
    modifier: Modifier,
    // pass flow of paging data to composable
    // https://developer.android.com/reference/kotlin/androidx/paging/compose/LazyPagingItems
    dictionaryPage: Flow<PagingData<DictionaryItem>>,
    isWordBookmarked: Boolean,
    onBookmarkWord: () -> Unit,
    showExamples: Boolean,
    showSynonyms: Boolean,
    showAntonyms: Boolean
) {
    val dictionaryPageItems = dictionaryPage.collectAsLazyPagingItems()

    if (dictionaryPageItems.loadState.refresh is LoadState.Loading ||
        dictionaryPageItems.loadState.append is LoadState.Loading ||
        dictionaryPageItems.loadState.prepend is LoadState.Loading)
        Loading(modifier)
    else if (dictionaryPageItems.loadState.append.endOfPaginationReached && dictionaryPageItems.itemCount == 0)
        NoResult(modifier = modifier)
    else if (dictionaryPageItems.itemCount > 0)
        SelectionContainer {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if(dictionaryPageItems.itemCount > 0)
                    items(count = dictionaryPageItems.itemCount) {
                        when (val item = dictionaryPageItems[it]) {
                            is DictionaryItem.ItemWord -> Word(
                                word = item.word,
                                isWordBookmarked = isWordBookmarked,
                                onBookmarkWord = onBookmarkWord
                            )
                            is DictionaryItem.ItemPhonetic -> Phonetic(
                                text = item.text,
                                audio = item.audioSource
                            )

                            is DictionaryItem.ItemMeaning -> Meaning(
                                index = item.index,
                                partOfSpeech = item.partOfSpeech
                            )
                            is DictionaryItem.ItemDefinition -> Definition(
                                index = item.index,
                                definition = item.definition,
                                example = item.example,
                                synonyms = item.synonyms,
                                antonyms = item.antonyms,
                                showExamples = showExamples,
                                showSynonyms = showSynonyms,
                                showAntonyms = showAntonyms
                            )
                            DictionaryItem.ItemSourceLabel -> SourceLabel()
                            is DictionaryItem.ItemSource -> Source(sourceUrl = item.url)
                            DictionaryItem.ItemLicenseLabel -> {   }
                            is DictionaryItem.ItemLicense -> { }
                            null -> {}
                        }
                    }

            }
        }
}

@Composable
private fun Loading(modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopSearchBar(
    isConnectionAvailable: Boolean,
    onSpeechToTextRequest: () -> Unit,
    focusRequester: FocusRequester,
    preSearchText: String,
    setPreSearchText: (String) -> Unit,
    search: (String) -> Unit,
    isActive: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    navigateToBack: () -> Unit,
    searchHistoryContent: @Composable ColumnScope.() -> Unit,
) {
    val searchBarPadding by animateDpAsState(targetValue = if (isActive) 0.dp else 16.dp)

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {

        ConnectionInformerBanner(isConnectionAvailable)


                SearchBar(
                    modifier = Modifier
                        .padding(searchBarPadding)

                        .focusRequester(focusRequester),
                    query = preSearchText,
                    onQueryChange = {
                        if (!isActive) onActiveChanged(true)
                        setPreSearchText(it)
                    },
                    onSearch = {
                        onActiveChanged(false)
                        preSearchText.let(search)
                    },
                    active = isActive,
                    onActiveChange = onActiveChanged,
                    leadingIcon = {

                        IconButton(
                            modifier = Modifier,
                            onClick = navigateToBack) {
                            Icon(
                                modifier = Modifier
                                    .size(32.dp),
                                painter = painterResource(id = DictionaryIcon.Back),
                                contentDescription = stringResource(id = R.string.back_to_main_screen)
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                               onSpeechToTextRequest()
                            }) {

                            Icon(
                                modifier = Modifier
                                    .size(32.dp),
                                painter = painterResource(id = DictionaryIcon.Mic),
                                contentDescription = ""
                            )
                        }
                    },
                content = searchHistoryContent
                )

        Divider(
            Modifier
                .height(2.dp)
                .fillMaxWidth())
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchHistory(
    preSearchText: String,
    searchHistoryPaging: Flow<PagingData<SearchHistoryItem>>,
    onSelected: (String) -> Unit,
    onDelete: (Long) -> Unit
) {
    val searchHistoryPagingItems = searchHistoryPaging.collectAsLazyPagingItems()
    var firstLoading: Boolean by remember{ mutableStateOf(true) }
    if (searchHistoryPagingItems.loadState.refresh == LoadState.Loading  && firstLoading
         ){
        Loading(modifier = Modifier.fillMaxSize())
    } else {
        firstLoading = false
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(
                count = searchHistoryPagingItems.itemCount,
                key = { (searchHistoryPagingItems[it] as? SearchHistoryItem.ItemSearchHistory?)?.searchId?:-1 },

            ) {
                val searchHistoryItem = searchHistoryPagingItems[it]
                when(val item = searchHistoryItem){
                    is SearchHistoryItem.ItemSearchHistory -> {
                        SearchResultItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelected(item.search)
                                }
                                .animateItemPlacement(),
                            searchResult = item.search,
                            searchedText = preSearchText,
                            onDelete = {
                                onDelete(item.searchId)
                            })

                    }
                    null -> {

                    }
                }

            }
            if (searchHistoryPagingItems.loadState.append == LoadState.Loading)
                item {
                    Loading(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }


        }
    }
}


@Composable
private fun Word(
    word: String,
    isWordBookmarked: Boolean,
    onBookmarkWord: () -> Unit,

) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
        ,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = word,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )

        IconButton(
            onClick = {
                onBookmarkWord()
            }) {
            Icon(
                painter = painterResource(id =
                if (isWordBookmarked) DictionaryIcon.BookMark
                else DictionaryIcon.BookMarkBorder
                ),
                contentDescription = stringResource(id = R.string.bookmark_the_word)
            )
        }
    }
}


@Composable
private fun Phonetic(text: String, audio: String?) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
    ) {

        Text(
            modifier = Modifier.padding(),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Light
        )

        if (audio?.isNotBlank() == true)
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                        playMedia(audio)
                }) {
                Icon(
                    painter = painterResource(id = DictionaryIcon.VolumeUp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
    }
}

private val mediaPlayer: MediaPlayer = MediaPlayer()
private fun playMedia(sourceUrl: String) {
    mediaPlayer.run {
        reset()
        setDataSource(sourceUrl)
        prepare()
        start()
    }
}


@Composable
private fun Meaning(index: String, partOfSpeech: String) {
    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = index + partOfSpeech,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun Definition(index: String,
                       definition: String,
                       example: String?,
                       synonyms: String,
                       antonyms: String,
                       showExamples: Boolean,
                       showSynonyms: Boolean,
                       showAntonyms: Boolean) {
    Text(
        modifier = Modifier.padding(),
        text = buildAnnotatedString {
            appendPrimaryColoredText(text = index)
            append(definition)

            if (example?.isNotBlank() == true && showExamples) {
                append("\n")
                appendPrimaryColoredText(text = stringResource(id = R.string.example).lowercase())
                append(example)
            }
            if (synonyms.isNotBlank() && showSynonyms) {
                append("\n")
                appendPrimaryColoredText(text = "${stringResource(
                    id = R.string.synonyms).lowercase()}: ")
                append(synonyms)
            }

            if (antonyms.isNotBlank() && showAntonyms) {
                append("\n")
                appendPrimaryColoredText(text = "${stringResource(id =
                R.string.antonyms).lowercase()}: ")
                append(antonyms)
            }
        }
    )
}

@Composable
private fun SourceLabel() {
    Title(title = stringResource(id = R.string.sources))
}

@Composable
public fun License(licenseName: String, licenseUrl: String) {
    Column {
        Title(title = stringResource(id = R.string.license))

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("${stringResource(id = R.string.name).lowercase()}: ")
            }
            append(licenseName)
        })

        val localUriHandler = LocalUriHandler.current
        ClickableText(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("${stringResource(id = R.string.url).lowercase()}: ")
                }
                append(licenseUrl)
            },
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
                textDecoration = TextDecoration.Underline
            ),
            onClick = {
                localUriHandler.openUri(licenseUrl)
            }
        )
    }
}

@Composable
private fun Source(sourceUrl: String) {
    val localUriHandler = LocalUriHandler.current
    ClickableText(
        text = buildAnnotatedString {
            append(sourceUrl)
        },
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onBackground,
            textDecoration = TextDecoration.Underline
        ),
        onClick = {
            localUriHandler.openUri(sourceUrl)
        }
    )
}

@Composable
private fun Title(title: String) {
    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun AnnotatedString.Builder.appendPrimaryColoredText(text: String) {
    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
        append(text)
    }
}






