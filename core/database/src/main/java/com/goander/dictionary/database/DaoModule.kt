package com.goander.dictionary.database

import com.goander.dictionary.database.dao.DictionaryDao
import com.goander.dictionary.database.dao.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    public fun provideDictionaryDao(dictionaryDatabase: DictionaryDatabase): DictionaryDao =
        dictionaryDatabase.dictionaryDao()

    @Provides
    public fun provideSearchHistoryDao(dictionaryDatabase: DictionaryDatabase): SearchHistoryDao =
        dictionaryDatabase.searchHistoryDao()


}