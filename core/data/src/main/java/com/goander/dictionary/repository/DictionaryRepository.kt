package com.goander.dictionary.repository

import androidx.paging.PagingData
import com.goander.dictionary.model.Dictionary
import com.goander.dictionary.model.SearchHistory
import com.goander.dictionary.model.WordWithDictionaries
import kotlinx.coroutines.flow.Flow
interface DictionaryRepository {

    public fun getSearchHistoryPaging(word: String): Flow<PagingData<SearchHistory>>

    public fun checkIfWordBookmarkedFlow(word: String): Flow<Boolean>

    public fun getDictionaryPage(word: String):  Flow<PagingData<WordWithDictionaries>>

    suspend fun bookmarkWord(word: String)

    suspend fun deleteAllWordsAndRemoteQueries()
}