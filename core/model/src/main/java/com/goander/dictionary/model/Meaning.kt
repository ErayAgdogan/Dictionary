package com.goander.dictionary.model

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String,
    val antonyms: List<String>,
    val synonyms: List<String>
)