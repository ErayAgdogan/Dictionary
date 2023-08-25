package com.goander.dictionary.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.goander.dictionary.database.DictionaryDatabase
import com.goander.dictionary.database.dao.DictionaryDao
import com.goander.dictionary.database.dao.RemoteDictionaryQueryDao
import com.goander.dictionary.database.dao.SearchHistoryDao
import com.goander.dictionary.model.SearchHistory
import com.goander.dictionary.model.WordWithDictionaries
import com.goander.dictionary.network.DictionaryNetworkDataSource
import com.goander.dictionary.repository.mapping.asSearchHistory
import com.goander.dictionary.repository.mapping.asWordWithDictionaries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryNetworkDataSource: DictionaryNetworkDataSource,
    private val dictionaryDatabase: DictionaryDatabase,
    private val searchHistoryDao: SearchHistoryDao,
    private val dictionaryDao: DictionaryDao,
    private val remoteDictionaryQueryDao: RemoteDictionaryQueryDao
): DictionaryRepository{


    override fun getSearchHistoryPaging(word: String): Flow<PagingData<SearchHistory>> {
        return Pager(
           PagingConfig(20)
        ) {
            searchHistoryDao.getSearchHistoryPaging(word)
        }.flow.map {
            it.map { it.asSearchHistory() }
        }
    }

    public override fun checkIfWordBookmarkedFlow(word: String): Flow<Boolean> =
        dictionaryDao.checkIfWordBookmarkedFlow(word)

    @OptIn(ExperimentalPagingApi::class)
    override fun getDictionaryPage(word: String): Flow<PagingData<WordWithDictionaries>> {

        val pager = Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 1,
            ),
            remoteMediator = DictionaryRemoteMediator(
                word,
                dictionaryNetworkDataSource,
                dictionaryDatabase
            )
        ) {
            dictionaryDao.getWordDictionaryPagingSource(word)
        }.flow.map { pagingData ->
            pagingData.map { it.asWordWithDictionaries() }
        }

        return pager
    }

    override suspend fun bookmarkWord(word: String): Long {
        return dictionaryDatabase.bookmarkedWordDao()
            .insertIfNotExistsDeleteIfExists(word)
    }

    override suspend fun deleteAllWordsAndRemoteQueries() {
        dictionaryDao.deleteAllWords()
        remoteDictionaryQueryDao.deleteAllRemoteDictionaryQuery()
    }
}