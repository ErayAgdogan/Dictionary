package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sources",
    indices = [
        Index(value = ["dictionary_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = DictionaryEntity::class,
            parentColumns = ["id"],
            childColumns = ["dictionary_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SourceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo("dictionary_id")
    val dictionaryId: Long,

    @ColumnInfo("source_url")
    val source_url: String

)
