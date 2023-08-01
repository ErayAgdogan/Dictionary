package com.goander.dictionary.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PhoneticWithLicense(
    @Embedded
    val phoneticEntity: PhoneticEntity,
    @Relation(
        entity = PhoneticLicenseEntity::class,
        entityColumn = "phonetic_id",
        parentColumn = "id",
    )
    val licenseEntity: PhoneticLicenseEntity?
)
