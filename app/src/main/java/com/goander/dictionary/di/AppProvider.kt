package com.goander.dictionary.di

import android.content.Context
import com.goander.dictionary.data.local.AppDatabase
import com.goander.dictionary.data.local.SearchHistoryDao
import com.goander.dictionary.data.remote.DictionaryApi
import com.goander.dictionary.repository.DictionaryRepository
import com.goander.dictionary.utility.DICTIONARY_URL
import com.goander.dictionary.utility.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppProvider {


    @Singleton
    @Provides
    public fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(DICTIONARY_URL)
            .build()
            .create(DictionaryApi::class.java)
    }

    @Singleton
    @Provides
    public fun provideConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

    @Singleton
    @Provides
    public fun provideDictionaryRepository(dictionaryApi: DictionaryApi, searchHistoryDao: SearchHistoryDao): DictionaryRepository {
        return DictionaryRepository(dictionaryApi, searchHistoryDao)
    }

    @Singleton
    @Provides
    public fun provideAppDatabase(@ApplicationContext context: Context):AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    public fun provideDictionaryDao(appDatabase: AppDatabase): SearchHistoryDao {
        return appDatabase.dictionaryDao()
    }

}
