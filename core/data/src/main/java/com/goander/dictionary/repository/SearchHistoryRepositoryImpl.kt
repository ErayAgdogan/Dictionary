package com.goander.dictionary.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.goander.dictionary.database.dao.SearchHistoryDao
import com.goander.dictionary.database.entity.SearchHistoryEntity
import com.goander.dictionary.model.SearchHistory
import com.goander.dictionary.repository.mapping.asSearchHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
): SearchHistoryRepository {

    override suspend fun insertSearchHistory(search: String) {
        searchHistoryDao.insert(SearchHistoryEntity(query = search))
    }

    public override suspend fun deleteSearchById(id: Long) {
        searchHistoryDao.deleteSearchById(id)
    }

    override fun getSearchHistoryPaging(word: String): Flow<PagingData<SearchHistory>> {
        return Pager(
            PagingConfig(20)
        ) {
            searchHistoryDao.getSearchHistoryPaging(word)
        }.flow.map {
            it.map { it.asSearchHistory() }
        }
    }

    override suspend fun deleteAllSearches() {
        searchHistoryDao.deleteAllSearches()
    }

}