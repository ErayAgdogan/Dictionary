package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "synonyms_meaning",
    foreignKeys = [
        ForeignKey(
            entity = DefinitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["meaning_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["meaning_id"])
    ]
)
data class SynonymMeaningEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "meaning_id")
    val meaningId:  Long,

    @ColumnInfo(name = "synonym")
    val synonym: String
)