package com.goander.dictionary.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithDictionaries(
    @Embedded
    val wordEntity: WordEntity,
    @Relation(
        entity = DictionaryEntity::class,
        parentColumn = "id",
        entityColumn = "word_id"
        )
    val dictionaryWithMeaningsAndPhoneticsList: List<DictionaryWithMeaningsAndPhonetics>
)
