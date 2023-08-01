package com.goander.dictionary.repository

import androidx.paging.PagingData
import com.goander.dictionary.model.BookmarkedWord
import kotlinx.coroutines.flow.Flow

interface BookmarkedWordsRepository {

    public fun getBookmarkedWordPagingData(query: String): Flow<PagingData<BookmarkedWord>>

}