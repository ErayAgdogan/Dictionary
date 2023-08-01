package com.goander.dictionary.repository

import androidx.paging.PagingData
import com.goander.dictionary.model.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    public suspend fun insertSearchHistory(search: String)

    public suspend fun deleteSearchById(id: Long)

    public fun getSearchHistoryPaging(word: String): Flow<PagingData<SearchHistory>>

    suspend fun deleteAllSearches()
}