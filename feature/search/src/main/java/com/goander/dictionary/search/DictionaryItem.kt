package com.goander.dictionary.search

import android.util.Log
import androidx.compose.ui.text.capitalize
import com.goander.dictionary.model.WordWithDictionaries

sealed interface DictionaryItem {

    public data class ItemWord(val word: String): DictionaryItem

    public data class ItemPhonetic(val text: String, val audioSource: String?): DictionaryItem

    public data class ItemMeaning(val index: String, val partOfSpeech: String): DictionaryItem

  //  public data class ItemAntonyms(val antonyms: String): DictionaryItem

  //  public data class ItemSynonyms(val synonyms: String): DictionaryItem

    public data class ItemDefinition(
        val index: String,
        val definition: String,
        val example: String,
        val synonyms: String,
        val antonyms: String
    ): DictionaryItem

    public object ItemSourceLabel: DictionaryItem

    public data class ItemSource(val url: String): DictionaryItem

    public object ItemLicenseLabel: DictionaryItem

    public data class ItemLicense(val name: String, val url: String): DictionaryItem

}

public fun WordWithDictionaries.asItems(): List<DictionaryItem> =
    buildList {
        add(DictionaryItem.ItemWord(word.capitalize()))
        addAll(dictionaries
            .flatMap { it.phonetics }
            .distinct()
            .map { DictionaryItem.ItemPhonetic(it.text, it.audio) }
        )
        var meaningIndex = 0
        dictionaries.forEach { dictionary ->
            dictionary.meanings.forEach { meaning ->
                add(DictionaryItem.ItemMeaning("${getAlphabetByIndex(meaningIndex++)}. ", meaning.partOfSpeech))
             /*
                if (meaning.synonyms.isNotEmpty())
                    add(DictionaryItem.ItemSynonyms(meaning.synonyms.joinToString(", ")))
                if (meaning.antonyms.isNotEmpty())
                    add(DictionaryItem.ItemAntonyms(meaning.antonyms.joinToString(", ")))
            */
                addAll(meaning.definitions.mapIndexed { index, definition ->


                    DictionaryItem.ItemDefinition(
                        index = "${index + 1}. ",
                        definition = definition.definition,
                        example = definition.example ?: "",
                        synonyms = definition.synonyms.joinToString(", "),
                        antonyms = definition.antonyms.joinToString(", ")
                    )
                }
                )
            }
        }
        add(DictionaryItem.ItemSourceLabel)
        addAll(dictionaries.flatMap { it.sourceUrls }.distinct().map { DictionaryItem.ItemSource(it) })
        add(DictionaryItem.ItemLicenseLabel)

    }


private fun getAlphabetByIndex(index: Int): Char = ('a' + index)