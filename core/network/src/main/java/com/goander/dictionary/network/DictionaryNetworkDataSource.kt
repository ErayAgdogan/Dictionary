package com.goander.dictionary.network

import com.goander.dictionary.network.model.NetworkDictionary


interface DictionaryNetworkDataSource {
    public suspend fun getResponse(word: String): NetworkDictionary?
}