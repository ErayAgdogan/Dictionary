package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_history",
)
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "query")
    val query: String,
    @ColumnInfo("created_at")
    val createdAt: Long = System.currentTimeMillis()
)
