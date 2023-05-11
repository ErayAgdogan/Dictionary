package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "phonetics",
    foreignKeys = [
        ForeignKey(
            entity = DictionaryEntity::class,
            parentColumns = ["id"],
            childColumns = ["dictionary_id"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index(value = ["dictionary_id"])
    ]
)
data class PhoneticEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "dictionary_id")
    val dictionaryId: Long,

    @ColumnInfo(name = "audio")
    val audio: String,

    @ColumnInfo(name = "text")
    val text: String
)