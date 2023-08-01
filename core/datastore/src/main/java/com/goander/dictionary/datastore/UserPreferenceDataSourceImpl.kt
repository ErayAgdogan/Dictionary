package com.goander.dictionary.datastore

import androidx.datastore.core.DataStore
import com.goander.dictionary.core.datastore.UserPreferences
import com.goander.dictionary.core.datastore.copy
import com.goander.dictionary.model.Font
import com.goander.dictionary.model.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
    ): UserPreferenceDataSource {

    override val userSettings: Flow<Setting> = dataStore.data.map {
        Setting(
            font = if (it.font.isBlank()) Font.DEFAULT else Font.valueOf(it.font),
            theme = it.themeColor,
            showExamples = it.showExamples,
            showSynonyms = it.showSynonyms,
            showAntonyms = it.showAntonyms
        )
    }

    public override suspend fun setFont(font: Font) {
        dataStore.updateData {
            it.copy {
                this.font = font.name
            }
        }
    }

    public override suspend fun setTheme(themeColor: Int) {
        dataStore.updateData {
            it.copy {
                this.themeColor = themeColor
            }
        }
    }

    public override suspend fun setShowExamples(showExamples: Boolean) {
        dataStore.updateData {
            it.copy {
                this.showExamples = showExamples
            }
        }
    }

    override suspend fun setShowSynonyms(showSynonyms: Boolean) {
        dataStore.updateData {
            it.copy {
                this.showSynonyms = showSynonyms
            }
        }
    }

    override suspend fun setShowAntonyms(showAntonyms: Boolean) {
        dataStore.updateData {
            it.copy {
                this.showAntonyms = showAntonyms
            }
        }
    }

}