package com.goander.dictionary.datastore

import androidx.datastore.core.Serializer
import com.goander.dictionary.core.datastore.UserPreferences
import com.goander.dictionary.model.Setting
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject


class UserPreferencesSerializer @Inject constructor(): Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences.newBuilder().apply {

        font = DEFAULT_FONT
        themeColor = DEFAULT_THEME
        showExamples = DEFAULT_SHOW_EXAMPLES
        showSynonyms = DEFAULT_SHOW_SYNONYMS
        showAntonyms = DEFAULT_SHOW_ANTONYMS
    }.build()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        return UserPreferences.parseFrom(input)
    }

    override suspend fun writeTo(
        t: UserPreferences,
        output: OutputStream
    ) = t.writeTo(output)
}