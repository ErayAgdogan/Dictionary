package com.goander.dictionary.repository.mapping

import com.goander.dictionary.database.entity.*
import com.goander.dictionary.database.entity.WordWithDictionaries
import com.goander.dictionary.model.*

fun SearchHistoryEntity.asSearchHistory() =
    SearchHistory(
        id = id,
        search = query
    )


fun MeaningWithDefinitions.asMeaning() =
    Meaning(
        partOfSpeech = meaning.partOfSpeech,
        definitions = definitions.map { it.asDefinition() },
        synonyms = synonyms.map { it.synonym },
        antonyms = antonyms.map { it.antonym }
    )


fun PhoneticWithLicense.asPhonetic() =
    Phonetic(
        audio = phoneticEntity.audio,
        text = phoneticEntity.text,
        sourceUrl = phoneticEntity.sourceUrl,
        license = licenseEntity?.asLicense()
    )

fun PhoneticLicenseEntity.asLicense() =
    License(
        name = name,
        url = url
    )


fun DefinitionWithAntonymsAndSynonyms.asDefinition() =
    Definition(
        definition = definition.definition,
        example = definition.example,
        antonyms = antonyms.map { it.antonym },
        synonyms = synonyms.map { it.synonym }
    )

fun DictionaryLicenseEntity.asLicense() =
    License(
        name = name,
        url = url
    )

fun DictionaryWithMeaningsAndPhonetics.asDictionary() =
    Dictionary(
        id = dictionary.id,
        phonetic = dictionary.phonetic?:"",
        phonetics = phonetics.map { it.asPhonetic() },
        meanings = meanings.map { it.asMeaning()  },
        license = license?.asLicense(),
        sourceUrls = sources.map { it.source_url  }
    )

fun WordWithDictionaries.asWordWithDictionaries() =
    com.goander.dictionary.model.WordWithDictionaries(
        word = wordEntity.word,
        dictionaries = dictionaryWithMeaningsAndPhoneticsList.map { it.asDictionary() }
    )

fun BookmarkedWordEntity.asBookmarkedWord(): BookmarkedWord =
    BookmarkedWord(word = word)