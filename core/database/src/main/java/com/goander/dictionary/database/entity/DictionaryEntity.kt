package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class DictionaryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "origin")
    val origin: String,

    @ColumnInfo(name = "phonetic")
    val phonetic: String,

    @ColumnInfo(name = "word")
    val word: String
)
