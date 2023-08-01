package com.goander.dictionary.repository.di

import com.goander.dictionary.database.dao.SearchHistoryDao
import com.goander.dictionary.network.DictionaryNetworkDataSource
import com.goander.dictionary.repository.BookmarkedWordsRepository
import com.goander.dictionary.repository.BookmarkedWordsRepositoryImpl
import com.goander.dictionary.repository.DictionaryRepository
import com.goander.dictionary.repository.DictionaryRepositoryImpl
import com.goander.dictionary.repository.SearchHistoryRepository
import com.goander.dictionary.repository.SearchHistoryRepositoryImpl
import com.goander.dictionary.repository.SettingRepository
import com.goander.dictionary.repository.SettingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    public fun provideDictionaryRepository(
        dictionaryRepositoryImpl: DictionaryRepositoryImpl
    ): DictionaryRepository

    @Binds
    public fun provideSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository

    @Binds
    public fun provideBookmarkedWordsRepository(
        bookmarkedWordsRepositoryImpl: BookmarkedWordsRepositoryImpl
    ): BookmarkedWordsRepository

    @Binds
    public fun provideSearchHistoryRepository(
        searchHistoryRepository: SearchHistoryRepositoryImpl
    ): SearchHistoryRepository
}