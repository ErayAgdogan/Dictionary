package com.goander.dictionary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "synonyms_definition",
    foreignKeys = [
        ForeignKey(
            entity = DefinitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["definition_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["definition_id"])
    ]
)
data class SynonymDefinitionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "definition_id")
    val definitionId:  Long,

    @ColumnInfo(name = "synonym")
    val synonym: String
)