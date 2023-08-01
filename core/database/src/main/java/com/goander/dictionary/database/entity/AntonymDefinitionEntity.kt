package com.goander.dictionary.database.entity

import androidx.room.*

@Entity(
    tableName = "antonyms_definition",
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
data class AntonymDefinitionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "definition_id")
    val definitionId: Long,

    @ColumnInfo(name = "antonym")
    val antonym: String
)
