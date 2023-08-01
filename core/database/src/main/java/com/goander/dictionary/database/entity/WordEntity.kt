package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words",
    indices = [
        Index(value = ["word"], unique = true)
    ]
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    public val id: Long = 0,
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    public val word: String,
)
