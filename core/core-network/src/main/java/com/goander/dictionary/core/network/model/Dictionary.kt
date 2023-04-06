package com.goander.dictionary.core.network.model

data class Dictionary(
    val id: Int,
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String
)



