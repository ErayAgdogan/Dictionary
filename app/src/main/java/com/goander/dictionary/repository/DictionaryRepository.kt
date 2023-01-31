package com.goander.dictionary.repository

import android.util.Log
import com.goander.dictionary.data.local.BaseDao
import com.goander.dictionary.data.local.SearchHistoryDao
import com.goander.dictionary.data.local.SearchHistoryEntity
import com.goander.dictionary.data.remote.Dictionary
import com.goander.dictionary.data.remote.DictionaryApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryRepository @Inject constructor(
   private val dictionaryApi: DictionaryApi,
   private val searchHistoryDao: SearchHistoryDao,
){
    public suspend fun getDictionary(word: String): Dictionary? {
        Log.e("DictionaryResponse","getting : " + word)
        return if (word.isNotBlank()) {
            searchHistoryDao.insert(SearchHistoryEntity(query = word))
            dictionaryApi.getResponse(word).also {
                Log.e("DictionaryResponse","is successful: " + it.isSuccessful)
                Log.e("DictionaryResponse","message: " + it.message())
                Log.e("DictionaryResponse","body: " + it.body())

                // Log.e("DictionaryResponse","is successful: " + it.)
            }.body()?.get(0)
        }
        else null
    }



}