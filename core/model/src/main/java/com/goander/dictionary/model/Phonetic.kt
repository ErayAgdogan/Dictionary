package com.goander.dictionary.model

data class Phonetic(
    val audio: String?,
    val text: String,
    val sourceUrl: String?,
    val license: License?
)