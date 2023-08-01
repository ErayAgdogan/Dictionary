package com.goander.dictionary.database.dao

import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.goander.dictionary.database.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao{

    @Insert
    public suspend fun insert(searchHistoryEntity: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE `query` = (SELECT `query` FROM search_history WHERE id = :id)")
    public suspend fun deleteSearchById(id: Long)

    @Query("SELECT * FROM search_history WHERE `query` LIKE '%' || :word || '%' GROUP BY `query` ORDER BY id DESC")
    public fun getSearchHistoryPaging(word: String): PagingSource<Int, SearchHistoryEntity>

    @Query("DELETE FROM search_history")
    suspend fun deleteAllSearches()

}