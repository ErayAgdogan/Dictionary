package com.goander.dictionary.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao: BaseDao<SearchHistoryEntity> {

    @Query("SELECT * FROM search_history ORDER BY id DESC")
    public fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>>

}