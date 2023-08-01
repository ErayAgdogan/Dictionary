package com.goander.dictionary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goander.dictionary.database.dao.BookmarkedWordDao
import com.goander.dictionary.database.dao.DictionaryDao
import com.goander.dictionary.database.dao.RemoteDictionaryQueryDao
import com.goander.dictionary.database.dao.SearchHistoryDao
import com.goander.dictionary.database.entity.*


@Database(entities = [
    SearchHistoryEntity::class,

    WordEntity::class,
    DictionaryEntity::class,
    MeaningEntity::class,
    PhoneticEntity::class,
    PhoneticLicenseEntity::class,
    DefinitionEntity::class,
    AntonymDefinitionEntity::class,
    AntonymMeaningEntity::class,
    SynonymDefinitionEntity::class,
    SynonymMeaningEntity::class,
    DictionaryLicenseEntity::class,
    SourceEntity::class,

    BookmarkedWordEntity::class,

    RemoteDictionaryQueryEntity::class,
                     ],
    version = 1,
    exportSchema = false)
abstract class DictionaryDatabase: RoomDatabase() {

    public abstract fun searchHistoryDao(): SearchHistoryDao
    public abstract fun dictionaryDao(): DictionaryDao

    public abstract fun bookmarkedWordDao(): BookmarkedWordDao

    public abstract fun remoteDictionaryQueryDao(): RemoteDictionaryQueryDao

    companion object {

    }

}