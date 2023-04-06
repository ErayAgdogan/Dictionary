package com.goander.dictionary.core.network.model

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)