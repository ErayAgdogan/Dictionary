package com.goander.dictionary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.goander.dictionary.database.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DictionaryDao {

    @Insert
    public abstract suspend fun insertDictionary(dictionaryEntity: DictionaryEntity): Long

    @Insert
    public abstract suspend fun insertAllMeanings(meaningEntities: List<MeaningEntity>): List<Long>

    @Insert
    public abstract suspend fun insertAllPhonetics(phoneticEntities: List<PhoneticEntity>): List<Long>

    @Insert
    public abstract suspend fun insertAllDefinitions(definitionEntities: List<DefinitionEntity>): List<Long>

    @Insert
    public abstract suspend fun insertAllAntonyms(antonymEntities: List<AntonymEntity>): List<Long>

    @Insert
    public abstract suspend fun insertAllSynonyms(synonymEntities: List<SynonymEntity>): List<Long>


    @Transaction
    open suspend fun executeTransaction(execute: suspend DictionaryDao.() -> Unit) {
        execute.invoke(this)
    }

    @Query("DELETE FROM dictionary WHERE word = :word")
    public abstract suspend fun deleteDictionaryByWord(word: String)


    @Query("SELECT EXISTS(SELECT 1 FROM dictionary WHERE word = :word LIMIT 1)")
    public abstract suspend fun checkIfDictionaryExistByWord(word: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM dictionary WHERE word = :word LIMIT 1)")
    public abstract fun checkIfDictionaryByWordExitsFlow(word: String): Flow<Boolean>

    @Query("SELECT * FROM dictionary WHERE word = :word")
    public abstract suspend fun getDictionary(word: String): DictionaryWithMeaningsAndPhonetics?
}