package com.goander.dictionary.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import androidx.paging.map
import com.goander.dictionary.connectivity.NetworkConnectivityObserver
import com.goander.dictionary.model.SearchHistory
import com.goander.dictionary.repository.DictionaryRepository
import com.goander.dictionary.repository.SearchHistoryRepository
import com.goander.dictionary.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

public const val SEARCH_KEYWORD = "search_keyword"
private const val PRE_SEARCH_TEXT = "pre_search_text"
private const val SHOW_SEARCH_HISTORY = "show_search_history"
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dictionaryRepository: DictionaryRepository,
    private val settingRepository: SettingRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
    private val networkConnectivityObserver: NetworkConnectivityObserver,
): ViewModel() {

    val networkConnectivity = networkConnectivityObserver.observe()

    private val searchKeyword: StateFlow<String> = savedStateHandle.getStateFlow(SEARCH_KEYWORD, "")
        .map { it.trim().lowercase() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = ""
        )

    val preSearchText: StateFlow<String> = savedStateHandle.getStateFlow(
        key = PRE_SEARCH_TEXT,
        initialValue = savedStateHandle[SEARCH_KEYWORD] ?:""
    )

    val showSearchHistory: StateFlow<Boolean> = savedStateHandle.getStateFlow(
        key = SHOW_SEARCH_HISTORY,
        // if search word is null or blank show search history at the start
        initialValue = savedStateHandle.get<String?>(SEARCH_KEYWORD).let { search ->
            search.isNullOrBlank()
        }
    )

    val isWordBookmarked: StateFlow<Boolean> = searchKeyword.flatMapLatest {
        dictionaryRepository.checkIfWordBookmarkedFlow(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

    private val userSettings = settingRepository.userSettings

    val showExamples = userSettings.map {
        it.showExamples
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = true
    )
    val showSynonyms = userSettings.map {
        it.showSynonyms
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = true
    )
    val showAntonyms = userSettings.map {
        it.showAntonyms
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = true
    )


    val searchUIState: Flow<PagingData<DictionaryItem>> = searchKeyword.flatMapLatest { search ->
        dictionaryRepository.getDictionaryPage(search).map {
            it.flatMap { wordWithDictionary ->
                wordWithDictionary.asItems()
            }
        }
    }.cachedIn(viewModelScope)



    val searchHistoryPaging: Flow<PagingData<SearchHistoryItem>> =  preSearchText.flatMapLatest {text ->
        dictionaryRepository.getSearchHistoryPaging(text).map { searchHistoryPaging ->
            searchHistoryPaging.map { searchHistory ->
                searchHistory.asItem()
            }
        }
    }


    public fun search(word: String) {
        savedStateHandle[PRE_SEARCH_TEXT] = word
        savedStateHandle[SEARCH_KEYWORD] = word
        viewModelScope.launch {
            searchHistoryRepository.insertSearchHistory(word)
        }
    }

    public fun setPreSearchText(text: String) {
        savedStateHandle[PRE_SEARCH_TEXT] = text
    }

    public fun showSearchHistory(show: Boolean) {
        savedStateHandle[SHOW_SEARCH_HISTORY] = show
    }

    public fun deleteSearch(id: Long) {
        viewModelScope.launch {
            searchHistoryRepository.deleteSearchById(id)
        }
    }

    fun bookmarkWord() {
        viewModelScope.launch {
            dictionaryRepository.bookmarkWord(searchKeyword.value)
        }
    }


}