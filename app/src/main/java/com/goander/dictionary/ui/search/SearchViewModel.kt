package com.goander.dictionary.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goander.dictionary.repository.DictionaryRepository
import com.goander.dictionary.utility.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
   private val savedStateHandle: SavedStateHandle,
   private val dictionaryRepository: DictionaryRepository,
   private val networkConnectivityObserver: NetworkConnectivityObserver,
): ViewModel() {

    private val SEARCH_KEYWORD = "search_keyword"

    val searchKeyword: StateFlow<String> = savedStateHandle.getStateFlow(SEARCH_KEYWORD,"")

    val networkConnectivity = networkConnectivityObserver.observe()


    @OptIn(ExperimentalCoroutinesApi::class)
    public val dictionaryFlow = searchKeyword
        .mapLatest{
                dictionaryRepository.getDictionary(it)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    public fun search(word: String) {
        savedStateHandle[SEARCH_KEYWORD] = word    }
}