package com.goander.dictionary.repository

import com.goander.dictionary.datastore.UserPreferenceDataSourceImpl
import com.goander.dictionary.model.Font
import javax.inject.Inject


class SettingRepositoryImpl @Inject constructor(
    private val userPreference: UserPreferenceDataSourceImpl
    ): SettingRepository{

    public override val userSettings = userPreference.userSettings

    public override suspend fun setFont(font: Font) {
        userPreference.setFont(font)
    }

    public override suspend fun setTheme(themeColor: Int) {
        userPreference.setTheme(themeColor)
    }

    override suspend fun setShowExamples(showExamples: Boolean) {
        userPreference.setShowExamples(showExamples)
    }

    override suspend fun setShowSynonyms(showSynonyms: Boolean) {
        userPreference.setShowSynonyms(showSynonyms)
    }

    override suspend fun setShowAntonyms(showAntonyms: Boolean) {
        userPreference.setShowAntonyms(showAntonyms)
    }
}