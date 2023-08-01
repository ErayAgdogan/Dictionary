package com.goander.dictionary.bookmark

import android.util.Log
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.goander.dictionary.repository.BookmarkedWordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

private const val SEARCH_KEY = "SEARCH"

@HiltViewModel
class BookmarkedWordsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    bookmarkedWordsRepository: BookmarkedWordsRepository
): ViewModel() {
    val searchStateFlow: StateFlow<String> = savedStateHandle.getStateFlow(SEARCH_KEY, "")

    public val bookmarkedWordsPagingData: Flow<PagingData<BookmarkedWordItem>> = searchStateFlow.flatMapLatest {
        bookmarkedWordsRepository.getBookmarkedWordPagingData(it).map { bookmarkedWordPagingData ->
            var alphabeth = ' '


            bookmarkedWordPagingData.map { bookmarkedWord ->
                val titleCaseWord = bookmarkedWord.word.replaceFirstChar(Char::titlecase)
                if (titleCaseWord.first() == alphabeth){
                    BookmarkedWordItem.Word(titleCaseWord)
                }
                else{
                    alphabeth = bookmarkedWord.word.first().uppercaseChar()
                    BookmarkedWordItem.WordWithAlphabet(
                        alphabet = alphabeth,
                        word = titleCaseWord
                    )
                }


            }
        }
    }.cachedIn(viewModelScope)

    public fun search(search: String) {
        savedStateHandle[SEARCH_KEY] = search
    }
}