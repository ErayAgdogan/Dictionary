package com.goander.dictionary.core.network

import com.goander.dictionary.core.network.model.Dictionary
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface DictionaryApi {

    @GET("en/{word}")
    public suspend fun getResponse(@Path("word") word: String): Response<List<Dictionary>>
}