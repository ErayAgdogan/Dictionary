package com.goander.dictionary.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmarked_words",
    indices = [
        Index(value = ["word"], unique = true)
    ]
)
data class BookmarkedWordEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val word: String,
    )