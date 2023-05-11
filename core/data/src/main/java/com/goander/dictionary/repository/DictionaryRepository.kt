package com.goander.dictionary.repository


import com.goander.dictionary.database.dao.DictionaryDao
import com.goander.dictionary.database.dao.SearchHistoryDao
import com.goander.dictionary.database.entity.AntonymEntity
import com.goander.dictionary.database.entity.SearchHistoryEntity
import com.goander.dictionary.database.entity.SynonymEntity
import com.goander.dictionary.model.Dictionary
import com.goander.dictionary.model.SearchHistory
import com.goander.dictionary.network.DictionaryNetworkDataSource
import com.goander.dictionary.repository.mapping.asDictionary
import com.goander.dictionary.repository.mapping.asEntity
import com.goander.dictionary.repository.mapping.asSearchHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryRepository @Inject constructor(
    private val dictionaryNetworkDataSource: DictionaryNetworkDataSource,
    private val searchHistoryDao: SearchHistoryDao,
    private val dictionaryDao: DictionaryDao
){

    public fun getDictionary(word: String): Flow<Dictionary?> =
        flow {
            if (word.isNotBlank()) {
                var dictionary: Dictionary?
                try {
                    dictionary = dictionaryNetworkDataSource.getResponse(word)?.asDictionary()

                } catch (e: Exception) {
                    dictionary = dictionaryDao.getDictionary(word)?.asDictionary()
                }
                if (word == dictionary?.word)
                    searchHistoryDao.insert(SearchHistoryEntity(query = word))
                emit(dictionary)
            } else emit(null)
        }


    public suspend fun saveDictionaryOrDeleteIfExists(dictionary: Dictionary) {
        if (dictionaryDao.checkIfDictionaryExistByWord(dictionary.word))
            dictionaryDao.deleteDictionaryByWord(word = dictionary.word)
        else
            dictionaryDao.executeTransaction {

        val dictionaryId = insertDictionary(dictionary.asEntity())
        val meaningEntityList = dictionary.meanings.map {
            it.asEntity(dictionaryId = dictionaryId)
        }
        val meaningIdList =  insertAllMeanings(meaningEntityList)

        insertAllPhonetics(dictionary.phonetics.map { it.asEntity(dictionaryId = dictionaryId) })

        meaningIdList.forEachIndexed { meaningIndex, meaningId ->
            val definitions = dictionary.meanings[meaningIndex].definitions
            val definitionIdList = insertAllDefinitions(
                definitions.map { it.asEntity(meaningId) }
            )
            definitionIdList.forEachIndexed { definitionIndex, definitionId ->
                insertAllAntonyms(definitions[definitionIndex].antonyms.map { antonyms ->
                    AntonymEntity(definitionId = definitionId, antonym = antonyms)
                })
                insertAllSynonyms(definitions[definitionIndex].synonyms.map { synonym ->
                    SynonymEntity(definitionId = definitionId, synonym = synonym)
                })
            }

        }
    }
    }



    public fun getSearchHistory(word: String): Flow<List<SearchHistory>> =
        searchHistoryDao.getSearchHistory(word).mapLatest { searchHistoryEntity->
            searchHistoryEntity.map { it.asSearchHistory() }
        }

    public suspend fun deleteSearchById(id: Long) {
        searchHistoryDao.deleteSearchById(id)
    }

    public fun checkIfWordExitsFlow(word: String): Flow<Boolean> =
        dictionaryDao.checkIfDictionaryByWordExitsFlow(word)


}