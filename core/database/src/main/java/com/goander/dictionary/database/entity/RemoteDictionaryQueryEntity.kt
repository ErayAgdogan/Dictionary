package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "remote_dictionary_query",
    indices = [Index(value = ["query"], unique = true)]
)
data class RemoteDictionaryQueryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val query: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
)
