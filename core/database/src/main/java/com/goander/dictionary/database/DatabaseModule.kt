package com.goander.dictionary.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDictionaryDatabase(@ApplicationContext context: Context): DictionaryDatabase  {
        Log.e("dictionary_database", "provided")
        return  Room.databaseBuilder(context, DictionaryDatabase::class.java, "dictionary_database").build()
    }

}