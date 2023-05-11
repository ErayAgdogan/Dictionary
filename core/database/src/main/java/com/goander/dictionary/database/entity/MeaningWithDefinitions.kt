package com.goander.dictionary.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MeaningWithDefinitions(
    @Embedded
    val meaning: MeaningEntity,
    @Relation(
        entity = DefinitionEntity::class,
        parentColumn = "id",
        entityColumn = "meaning_id"
    )
    val definitions: List<DefinitionWithAntonymsAndSynonyms>,
)