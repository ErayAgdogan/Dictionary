package com.goander.dictionary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goander.dictionary.database.dao.DictionaryDao
import com.goander.dictionary.database.dao.SearchHistoryDao
import com.goander.dictionary.database.entity.*


@Database(entities = [
    SearchHistoryEntity::class,
    DictionaryEntity::class,
    MeaningEntity::class,
    PhoneticEntity::class,
    DefinitionEntity::class,
    AntonymEntity::class,
    SynonymEntity::class,
                     ],
    version = 1,
    exportSchema = false)
abstract class DictionaryDatabase: RoomDatabase() {

    public abstract fun searchHistoryDao(): SearchHistoryDao
    public abstract fun dictionaryDao(): DictionaryDao

    companion object {

    }

}