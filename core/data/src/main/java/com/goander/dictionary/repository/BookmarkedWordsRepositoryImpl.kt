package com.goander.dictionary.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.goander.dictionary.database.dao.BookmarkedWordDao
import com.goander.dictionary.model.BookmarkedWord
import com.goander.dictionary.repository.mapping.asBookmarkedWord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkedWordsRepositoryImpl @Inject constructor(
    private val bookmarkedWordDao: BookmarkedWordDao
): BookmarkedWordsRepository {

    public override fun getBookmarkedWordPagingData(query: String): Flow<PagingData<BookmarkedWord>> {
        return Pager(
            config = PagingConfig(pageSize = 20)
        ) {
            bookmarkedWordDao.getBookmarkedWordsBySearchPagingSource(query)
        }.flow.map {
            it.map { it.asBookmarkedWord() }
        }
    }

}