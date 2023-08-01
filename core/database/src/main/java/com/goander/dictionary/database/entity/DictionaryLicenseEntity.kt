package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dictionary_license",
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
data class DictionaryLicenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo("dictionary_id")
    val dictionaryId: Long,
    val name: String,
    val url: String
)
