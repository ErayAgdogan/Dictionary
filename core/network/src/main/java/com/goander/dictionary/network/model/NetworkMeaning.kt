package com.goander.dictionary.network.model


data class NetworkMeaning(
    val definitions: List<NetworkDefinition>,
    val partOfSpeech: String,
    val antonyms: List<String>,
    val synonyms: List<String>
)

