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

    @Relation(
        entity = SynonymMeaningEntity::class,
        parentColumn = "id",
        entityColumn = "meaning_id"
    )
    val synonyms: List<SynonymMeaningEntity>,


    @Relation(
        entity = AntonymMeaningEntity::class,
        parentColumn = "id",
        entityColumn = "meaning_id"
    )
    val antonyms: List<AntonymMeaningEntity>,
)