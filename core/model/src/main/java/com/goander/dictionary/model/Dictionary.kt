package com.goander.dictionary.model

data class Dictionary(
    val id: Long,
    val meanings: List<Meaning>,
    val phonetic: String?,
    val phonetics: List<Phonetic>,
    val license: License?,
    val sourceUrls: List<String>
)



