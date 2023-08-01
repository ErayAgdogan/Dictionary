package com.goander.dictionary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.goander.dictionary.database.entity.RemoteDictionaryQueryEntity

@Dao
interface RemoteDictionaryQueryDao {

    @Insert
    public suspend fun insert(remoteDictionaryQueryEntity: RemoteDictionaryQueryEntity)

    @Query("DELETE FROM remote_dictionary_query WHERE `query` = :query")
    public suspend fun deleteDictionaryRemoteQuery(query: String)

    @Query("SELECT created_at FROM remote_dictionary_query WHERE `query` = :query LIMIT 1")
    suspend fun getCreatedAt(query: String): Long?

    @Query("DELETE FROM remote_dictionary_query")
    public suspend fun deleteAllRemoteDictionaryQuery()
}