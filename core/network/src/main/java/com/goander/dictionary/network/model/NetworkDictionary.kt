package com.goander.dictionary.network.model

data class NetworkDictionary(
    val id: Long,
    val meanings: List<NetworkMeaning>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<NetworkPhonetic>,
    val word: String
)






