package com.goander.dictionary.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DictionaryWithMeaningsAndPhonetics(
    @Embedded
    val dictionary: DictionaryEntity,

    @Relation(
        entity = MeaningEntity::class,
        parentColumn = "id",
        entityColumn = "dictionary_id"
    )
    val meanings: List<MeaningWithDefinitions>,

    @Relation(
        entity = PhoneticEntity::class,
        parentColumn = "id",
        entityColumn = "dictionary_id"
    )
    val phonetics: List<PhoneticEntity>,

)



