package com.goander.dictionary.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.goander.dictionary.database.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface  DictionaryDao {

    @Insert
    public suspend fun insertWord(wordEntity: WordEntity): Long

    @Insert
    public suspend fun insertDictionary(dictionaryEntity: DictionaryEntity): Long

    @Insert
    abstract suspend fun insertMeaning(meaning: MeaningEntity): Long

    @Insert
    public suspend fun insertAllMeanings(meaningEntities: List<MeaningEntity>): List<Long>

    @Insert
    public suspend fun insertAllPhonetics(phoneticEntities: List<PhoneticEntity>): List<Long>

    @Insert
    public suspend fun insertPhonetic(phoneticEntities: PhoneticEntity): Long

    @Insert
    public suspend fun insertPhoneticLicense(phoneticLicenseEntity: PhoneticLicenseEntity): Long

    @Insert
    suspend fun insertDefinition(definition: DefinitionEntity): Long

    @Insert
    public suspend fun insertAllDefinitions(definitionEntities: List<DefinitionEntity>): List<Long>

    @Insert
    suspend fun insertAllAntonymMeanings(antonymMeanings: List<AntonymMeaningEntity>): List<Long>

    @Insert
    public suspend fun insertAllAntonyms(antonymEntities: List<AntonymDefinitionEntity>): List<Long>

    @Insert
    suspend fun insertAllSynonymMeanings(synonymMeanings: List<SynonymMeaningEntity>): List<Long>

    @Insert
    public suspend fun insertAllSynonyms(synonymEntities: List<SynonymDefinitionEntity>): List<Long>

    @Insert
    public suspend fun insertLicense(dictionaryLicenseEntity: DictionaryLicenseEntity): Long

    @Insert
    public suspend fun insertAllSources(sourceEntityList: List<SourceEntity>): List<Long>

    @Query("DELETE FROM words WHERE word = :word")
    public suspend fun deleteWord(word: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_words WHERE word = :word LIMIT 1)")
    public fun checkIfWordBookmarkedFlow(word: String): Flow<Boolean>

    @Transaction
    @Query("SELECT * FROM words WHERE word = :word")
    public fun getWordDictionaryPagingSource(word: String): PagingSource<Int, WordWithDictionaries>


    @Query("DELETE FROM words")
    public suspend fun deleteAllWords()
}