package com.goander.dictionary.repository.mapping

import com.goander.dictionary.database.entity.*
import com.goander.dictionary.model.*

fun SearchHistoryEntity.asSearchHistory() =
    SearchHistory(
        id = id,
        search = query
    )

fun Dictionary.asEntity() =
    DictionaryEntity(
        word = word,
        phonetic = phonetic,
        origin = origin
    )

fun Meaning.asEntity(dictionaryId: Long) =
    MeaningEntity(
        dictionaryId = dictionaryId,
        partOfSpeech = partOfSpeech
    )

fun MeaningWithDefinitions.asMeaning() =
    Meaning(
        partOfSpeech = meaning.partOfSpeech,
        definitions = definitions.map { it.asDefinition() }
    )

fun Phonetic.asEntity(dictionaryId: Long) =
    PhoneticEntity(
        dictionaryId = dictionaryId,
        text = text,
        audio = audio
    )

fun PhoneticEntity.asPhonetic() =
    Phonetic(
        audio = audio,
        text = text
    )

fun Definition.asEntity(meaningId: Long) =
    DefinitionEntity(
        meaningId = meaningId,
        definition = definition,
        example = example
    )

fun DefinitionWithAntonymsAndSynonyms.asDefinition() =
    Definition(
        definition = definition.definition,
        example = definition.example,
        antonyms = antonyms.map { it.antonym },
        synonyms = synonyms.map { it.synonym }
    )

fun DictionaryWithMeaningsAndPhonetics.asDictionary() =
    Dictionary(
        id = dictionary.id,
        word = dictionary.word,
        origin = dictionary.origin,
        phonetic = dictionary.phonetic,
        phonetics = phonetics.map { it.asPhonetic() },
        meanings = meanings.map { it.asMeaning()  }
    )
