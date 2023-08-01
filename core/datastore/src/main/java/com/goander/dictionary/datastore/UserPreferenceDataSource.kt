package com.goander.dictionary.datastore

import com.goander.dictionary.model.Font
import com.goander.dictionary.model.Setting
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    val userSettings: Flow<Setting>
    public suspend fun setFont(font: Font)
    suspend fun setTheme(themeColor: Int)
    suspend fun setShowExamples(showExamples: Boolean)
    suspend fun setShowSynonyms(showSynonyms: Boolean)
    suspend fun setShowAntonyms(showAntonyms: Boolean)
}