package com.goander.dictionary.search

import androidx.paging.PagingData
import com.goander.dictionary.model.Dictionary
import com.goander.dictionary.model.SearchHistory

sealed interface SearchUIState {

    public object Loading: SearchUIState
    public object NoResult: SearchUIState
    public data class Result(val dictionary: Dictionary): SearchUIState

    public data class ResultPaging(val dictionary: PagingData<Dictionary>): SearchUIState

}