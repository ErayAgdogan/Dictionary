package com.goander.dictionary.database.dao

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

    @Query("DELETE FROM search_history WHERE id = :id")
    public suspend fun deleteSearchById(id: Long)

    @Query("SELECT * FROM search_history WHERE :word = '' OR `query` LIKE '%' || :word || '%' GROUP BY CASE WHEN :word = '' THEN id ELSE `query` END ORDER BY id DESC")
    public fun getSearchHistory(word: String): Flow<List<SearchHistoryEntity>>

}