package com.goander.dictionary.database.entity

import androidx.room.*

@Entity(
    tableName = "antonyms_meaning",
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
data class AntonymMeaningEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "meaning_id")
    val meaningId: Long,

    @ColumnInfo(name = "antonym")
    val antonym: String
)
