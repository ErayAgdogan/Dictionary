package com.goander.dictionary.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.goander.dictionary.database.DictionaryDatabase
import com.goander.dictionary.database.dao.DictionaryDao
import com.goander.dictionary.database.dao.RemoteDictionaryQueryDao
import com.goander.dictionary.database.entity.DictionaryWithMeaningsAndPhonetics
import com.goander.dictionary.database.entity.RemoteDictionaryQueryEntity
import com.goander.dictionary.database.entity.WordEntity
import com.goander.dictionary.database.entity.WordWithDictionaries
import com.goander.dictionary.network.DictionaryNetworkDataSource
import com.goander.dictionary.network.model.NetworkDictionary
import com.goander.dictionary.repository.mapping.asAntonymDefinitionEntity
import com.goander.dictionary.repository.mapping.asDefinitionEntity
import com.goander.dictionary.repository.mapping.asDictionaryEntity
import com.goander.dictionary.repository.mapping.asDictionaryLicenseEntity
import com.goander.dictionary.repository.mapping.asAntonymMeaningEntity
import com.goander.dictionary.repository.mapping.asLicense
import com.goander.dictionary.repository.mapping.asMeaningEntity
import com.goander.dictionary.repository.mapping.asPhoneticEntity
import com.goander.dictionary.repository.mapping.asPhoneticLicenseEntity
import com.goander.dictionary.repository.mapping.asSourceEntity
import com.goander.dictionary.repository.mapping.asSynonymDefinitionEntity
import com.goander.dictionary.repository.mapping.asSynonymMeaningEntity
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private val REFRESH_RATE: TimeUnit = TimeUnit.DAYS

@OptIn(ExperimentalPagingApi::class)
class DictionaryRemoteMediator @Inject constructor(
    private val word: String,
    private val dictionaryNetworkDataSource: DictionaryNetworkDataSource,
    private val dictionaryDatabase: DictionaryDatabase

): RemoteMediator<Int, WordWithDictionaries>() {
    private val dictionaryDao: DictionaryDao = dictionaryDatabase.dictionaryDao()
    private val remoteDictionaryQueryDao: RemoteDictionaryQueryDao =
        dictionaryDatabase.remoteDictionaryQueryDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WordWithDictionaries>
    ): MediatorResult {
        return try {
            // data is being loaded at once
            // no need to handle for append and prepend
            if (loadType != LoadType.REFRESH)
                return MediatorResult.Success(endOfPaginationReached = true)

            val dictionaryRemoteResponse: List<NetworkDictionary>? = dictionaryNetworkDataSource.getResponse(word)

            dictionaryDatabase.withTransaction {
                dictionaryDao.deleteWord(word)
                remoteDictionaryQueryDao.deleteDictionaryRemoteQuery(word)
                remoteDictionaryQueryDao.insert(RemoteDictionaryQueryEntity(query = word))
                Log.d("DictionaryRemoteMediato", dictionaryRemoteResponse.toString())
                if (!dictionaryRemoteResponse.isNullOrEmpty()) {
                    saveDictionary(dictionaryRemoteResponse, word)
                }
            }


            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("DictionaryRemoteMediato", "IOException", e)
            // In case there is any network error
            // still need to return value from database so return success
            MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("DictionaryRemoteMediato", "Exception", e)
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout: Long = TimeUnit.MILLISECONDS.convert(1, REFRESH_RATE)
        val createdTime = remoteDictionaryQueryDao.getCreatedAt(word)
        if (createdTime == null)
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        val passedTime = System.currentTimeMillis() - createdTime
        val isCacheTimeout = passedTime <= cacheTimeout
        return if (isCacheTimeout)
        {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun saveDictionary(
        networkDictionaryList: List<NetworkDictionary>,
        word: String
    ) {
        val wordId: Long = dictionaryDao.insertWord(WordEntity(word = word))
        for (networkDictionary in networkDictionaryList) {
            val dictionaryId = dictionaryDao
                .insertDictionary(networkDictionary.asDictionaryEntity(wordId))

            for (meaning in networkDictionary.meanings) {
                val meaningId = dictionaryDao.insertMeaning(meaning.asMeaningEntity(dictionaryId))
                for (definition in meaning.definitions) {
                    val definitionId = dictionaryDao.insertDefinition(
                        definition.asDefinitionEntity(meaningID = meaningId)
                    )
                    dictionaryDao.insertAllSynonyms(
                        definition.synonyms.map { it.asSynonymDefinitionEntity(definitionId) }
                    )
                    dictionaryDao.insertAllAntonyms(
                        definition.antonyms.map { it.asAntonymDefinitionEntity(definitionId) }
                    )
                }
                dictionaryDao.insertAllAntonymMeanings(
                    meaning.antonyms.map { it.asAntonymMeaningEntity(meaningId) }
                )
                dictionaryDao.insertAllSynonymMeanings(
                    meaning.synonyms.map { it.asSynonymMeaningEntity(meaningId) }
                )
            }

            networkDictionary.phonetics.forEach { phonetic ->
                val phoneticId: Long = dictionaryDao
                    .insertPhonetic(phonetic.asPhoneticEntity(dictionaryId =dictionaryId))
                val phoneticLicenseEntity = phonetic.license?.asPhoneticLicenseEntity(phoneticId = phoneticId)
                if (phoneticLicenseEntity != null)
                    dictionaryDao.insertPhoneticLicense(phoneticLicenseEntity)

            }
            dictionaryDao.insertAllPhonetics(networkDictionary.phonetics.map {
                it.asPhoneticEntity(
                    dictionaryId
                )
            })
            dictionaryDao.insertLicense(
                networkDictionary.license.asDictionaryLicenseEntity(
                    dictionaryId = dictionaryId
                )
            )
            dictionaryDao.insertAllSources(networkDictionary.sourceUrls.map {
                it.asSourceEntity(
                    dictionaryId
                )
            })

        }
    }


}