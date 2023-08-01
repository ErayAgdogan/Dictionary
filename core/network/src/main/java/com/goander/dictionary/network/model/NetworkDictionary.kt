package com.goander.dictionary.network.model

data class NetworkDictionary(
    val id: Long,
    val meanings: List<NetworkMeaning>,
    val phonetic: String?,
    val phonetics: List<NetworkPhonetic>,
    val word: String,
    val license: NetworkLicense,
    val sourceUrls: List<String>
)






