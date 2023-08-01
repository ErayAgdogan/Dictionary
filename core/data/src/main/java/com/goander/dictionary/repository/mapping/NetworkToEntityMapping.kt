package com.goander.dictionary.repository.mapping

import com.goander.dictionary.database.entity.AntonymDefinitionEntity
import com.goander.dictionary.database.entity.AntonymMeaningEntity
import com.goander.dictionary.database.entity.DefinitionEntity
import com.goander.dictionary.database.entity.DictionaryEntity
import com.goander.dictionary.database.entity.DictionaryLicenseEntity
import com.goander.dictionary.database.entity.MeaningEntity
import com.goander.dictionary.database.entity.PhoneticEntity
import com.goander.dictionary.database.entity.PhoneticLicenseEntity
import com.goander.dictionary.database.entity.SourceEntity
import com.goander.dictionary.database.entity.SynonymDefinitionEntity
import com.goander.dictionary.database.entity.SynonymMeaningEntity
import com.goander.dictionary.network.model.NetworkDefinition
import com.goander.dictionary.network.model.NetworkDictionary
import com.goander.dictionary.network.model.NetworkLicense
import com.goander.dictionary.network.model.NetworkMeaning
import com.goander.dictionary.network.model.NetworkPhonetic


public fun NetworkDefinition.asDefinitionEntity(meaningID: Long): DefinitionEntity =
    DefinitionEntity(
        meaningId = meaningID,
        definition = this.definition?:"",
        example = this.example?:"",
    )

public fun NetworkDictionary.asDictionaryEntity(wordId: Long): DictionaryEntity =
    DictionaryEntity(
        wordId = wordId,
        phonetic = this.phonetic?:"",
    )


public fun NetworkPhonetic.asPhoneticEntity(dictionaryId: Long): PhoneticEntity =
    PhoneticEntity(
        dictionaryId = dictionaryId,
        audio = this.audio?:"",
        text = this.text?:"",
        sourceUrl = sourceUrl
    )

public fun NetworkLicense.asPhoneticLicenseEntity(phoneticId: Long): PhoneticLicenseEntity =
    PhoneticLicenseEntity(
        phoneticId = phoneticId,
        name = name,
        url = url
    )

public fun NetworkMeaning.asMeaningEntity(dictionaryId: Long): MeaningEntity =
    MeaningEntity(
        dictionaryId = dictionaryId,
        partOfSpeech = this.partOfSpeech,
        )

public fun NetworkLicense.asDictionaryLicenseEntity(dictionaryId: Long): DictionaryLicenseEntity =
    DictionaryLicenseEntity(
        dictionaryId = dictionaryId,
        name = name,
        url = url
    )



public fun String.asSourceEntity(dictionaryId: Long): SourceEntity =
    SourceEntity(
        dictionaryId = dictionaryId,
        source_url = this
    )

public fun String.asAntonymMeaningEntity(meaningId: Long): AntonymMeaningEntity =
    AntonymMeaningEntity(
        meaningId = meaningId,
        antonym = this
    )

public fun String.asSynonymMeaningEntity(meaningId: Long): SynonymMeaningEntity =
    SynonymMeaningEntity(
        meaningId = meaningId,
        synonym = this
    )


public fun String.asAntonymDefinitionEntity(definitionId: Long): AntonymDefinitionEntity =
    AntonymDefinitionEntity(
        definitionId = definitionId,
        antonym = this
    )

public fun String.asSynonymDefinitionEntity(definitionId: Long): SynonymDefinitionEntity =
    SynonymDefinitionEntity(
        definitionId = definitionId,
        synonym = this
    )
