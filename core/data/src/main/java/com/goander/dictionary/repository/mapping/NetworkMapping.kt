package com.goander.dictionary.repository.mapping

import com.goander.dictionary.model.Definition
import com.goander.dictionary.model.Dictionary
import com.goander.dictionary.model.Meaning
import com.goander.dictionary.model.Phonetic
import com.goander.dictionary.network.model.*





public fun NetworkDefinition.asDefinition(): Definition =
    Definition(
        antonyms = this.antonyms,
        definition = this.definition?:"",
        example = this.example?:"",
        synonyms = this.synonyms
    )

public fun NetworkDictionary.asDictionary(): Dictionary =
    Dictionary(
        id = this.id,
        meanings = this.meanings.map{ it.asMeaning() },
        origin = this.origin?: "",
        phonetic = this.phonetic?:"",
        phonetics = this.phonetics.map{ it.asPhonetic() },
        word = this.word?:""
    )


public fun NetworkPhonetic.asPhonetic(): Phonetic =
    Phonetic(
        audio = this.audio?:"",
        text = this.text?:""
    )

public fun NetworkMeaning.asMeaning(): Meaning =
    Meaning(
        definitions = this.definitions.map{ it.asDefinition() },
        partOfSpeech = this.partOfSpeech?:""
    )
