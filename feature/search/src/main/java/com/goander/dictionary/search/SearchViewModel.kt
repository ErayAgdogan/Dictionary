package com.goander.dictionary.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goander.dictionary.connectivity.NetworkConnectivityObserver
import com.goander.dictionary.model.Dictionary
import com.goander.dictionary.model.SearchHistory
import com.goander.dictionary.repository.DictionaryRepository
import com.goander.dictionary.repository.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.goander.dictionary.repository.Result
import javax.inject.Inject

public const val SEARCH_KEYWORD = "SEARCH_KEYWORD"
private const val PRE_SEARCH_TEXT = "PRE_SEARCH_TEXT"
private const val SHOW_SEARCH_HISTORY = "SHOW_SEARCH_HISTORY"
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dictionaryRepository: DictionaryRepository,
    private val networkConnectivityObserver: NetworkConnectivityObserver,
): ViewModel() {

    val networkConnectivity = networkConnectivityObserver.observe()

    private val searchKeyword: StateFlow<String> = savedStateHandle.getStateFlow(SEARCH_KEYWORD, "")

    val preSearchText: StateFlow<String> = savedStateHandle.getStateFlow(
        key = PRE_SEARCH_TEXT,
        initialValue = savedStateHandle[SEARCH_KEYWORD] ?:""
    )
    private val showSearchHistory = savedStateHandle.getStateFlow(SHOW_SEARCH_HISTORY, true);

    val isWordSaved: StateFlow<Boolean> = searchKeyword.flatMapLatest {
        dictionaryRepository.checkIfWordExitsFlow(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

    private val dictionary: Flow<Result<Dictionary?>> = searchKeyword.flatMapMerge {
        dictionaryRepository.getDictionary(it).asResult()
    }

    private val searchHistoryWithSearchedText: Flow<Result<Pair<List<SearchHistory>, String>>> =
        preSearchText.flatMapLatest {text ->
            dictionaryRepository.getSearchHistory(text).map { Pair(it, text) }
        }.asResult()

    val searchUIState: StateFlow<SearchUIState> =
        combine(dictionary, searchHistoryWithSearchedText, showSearchHistory) {
                dictionary, searchHistory, showSearchHistory->
            if (showSearchHistory && searchHistory is Result.Success)
                SearchUIState.ShowSearchHistory(searchHistory.result.second, searchHistory.result.first)
            else if (showSearchHistory && searchHistory is Result.Loading)
                SearchUIState.Loading
            else if (dictionary is Result.Success && dictionary.result != null)
                SearchUIState.Result(dictionary.result!!)
            else if (dictionary is Result.Loading)
                SearchUIState.Loading
            else
                SearchUIState.NoResult
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = SearchUIState.Loading
        )


    public fun search(word: String) {
        savedStateHandle[PRE_SEARCH_TEXT] = word
        savedStateHandle[SEARCH_KEYWORD] = word
        savedStateHandle[SHOW_SEARCH_HISTORY] = word.isBlank()
    }

    public fun setPreSearchText(text: String) {
        savedStateHandle[PRE_SEARCH_TEXT] = text
        savedStateHandle[SHOW_SEARCH_HISTORY] = true
    }

    public fun deleteSearch(id: Long) {
        viewModelScope.launch {
            dictionaryRepository.deleteSearchById(id)
        }
    }

    public fun saveDictionary(dictionary: Dictionary) {
        viewModelScope.launch {
            dictionaryRepository.saveDictionaryOrDeleteIfExists(dictionary)
        }
    }

}