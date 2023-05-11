package com.goander.dictionary.network.di

import com.goander.dictionary.network.DictionaryNetwork
import com.goander.dictionary.network.DictionaryNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DictionaryNetworkModule {

    @Provides
    @Singleton
    public fun providesDictionaryNetworkDataSource(dictionaryNetwork: DictionaryNetwork): DictionaryNetworkDataSource {
        return dictionaryNetwork
    }
}