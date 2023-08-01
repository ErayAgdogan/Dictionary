package com.goander.dictionary.network.model

data class NetworkPhonetic(
    val audio: String,
    val text: String,
    val sourceUrl: String?,
    val license: NetworkLicense?
)
