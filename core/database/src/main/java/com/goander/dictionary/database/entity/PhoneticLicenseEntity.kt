package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "phonetic_licenses",
    indices = [
        Index(value = ["phonetic_id"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = PhoneticEntity::class,
            parentColumns = ["id"],
            childColumns = ["phonetic_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PhoneticLicenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "phonetic_id")
    val phoneticId: Long,
    val name: String,
    val url: String
)
