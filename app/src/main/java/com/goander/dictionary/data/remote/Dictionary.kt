package com.goander.dictionary.data.remote

data class Dictionary(
    override val id: Int,
    override val meanings: List<Meaning>,
    override val origin: String,
    override val phonetic: String,
    override val phonetics: List<Phonetic>,
    override val word: String
):Dic

interface Dic {
    val id: Int
    val meanings: List<Meaning>
    val origin: String
    val phonetic: String
    val phonetics: List<Phonetic>
    val word: String
}