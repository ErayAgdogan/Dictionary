package com.goander.dictionary.network

import android.util.Log
import com.goander.dictionary.network.model.NetworkDictionary
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

private const val DICTIONARY_BASE_URL: String = "https://api.dictionaryapi.dev/api/v2/entries/"

@Singleton
class DictionaryNetworkDataSourceImpl @Inject constructor(): DictionaryNetworkDataSource {

    private val dictionaryApi: DictionaryApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(DICTIONARY_BASE_URL)
        .build()
        .create(DictionaryApi::class.java)

    override suspend fun getResponse(word: String): List<NetworkDictionary>? {
        return dictionaryApi.getResponse(word).body()
    }
}