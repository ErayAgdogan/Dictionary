package com.goander.dictionary.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DefinitionWithAntonymsAndSynonyms(
    @Embedded
    val definition: DefinitionEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "definition_id"
    )
    val antonyms: List<AntonymEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "definition_id"
    )
    val synonyms: List<SynonymEntity>
)