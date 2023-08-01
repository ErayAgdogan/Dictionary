package com.goander.dictionary.repository

import com.goander.dictionary.model.Font
import com.goander.dictionary.model.Setting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    public val userSettings: Flow<Setting>
    public suspend fun setFont(font: Font)

    public suspend fun setTheme(themeColor: Int)

    public suspend fun setShowExamples(showExamples: Boolean)

    suspend fun setShowSynonyms(showSynonyms: Boolean)

    suspend fun setShowAntonyms(showAntonyms: Boolean)

}