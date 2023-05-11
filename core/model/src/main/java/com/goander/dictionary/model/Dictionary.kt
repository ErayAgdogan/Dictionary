package com.goander.dictionary.model

data class Dictionary(
    val id: Long,
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String
)



