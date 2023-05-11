package com.goander.dictionary.search

import com.goander.dictionary.model.Dictionary
import com.goander.dictionary.model.SearchHistory

sealed interface SearchUIState {
    public data class ShowSearchHistory(val searchedText: String, val searchHistoryList: List<SearchHistory>): SearchUIState
    public object Loading: SearchUIState
    public object NoResult: SearchUIState
    public data class Result(val dictionary: Dictionary): SearchUIState

}