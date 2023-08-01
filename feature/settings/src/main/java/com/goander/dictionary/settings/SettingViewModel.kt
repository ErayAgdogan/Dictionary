package com.goander.dictionary.settings

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goander.dictionary.model.Font
import com.goander.dictionary.model.Setting
import com.goander.dictionary.repository.DictionaryRepository
import com.goander.dictionary.repository.SearchHistoryRepository
import com.goander.dictionary.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val settingsRepository: SettingRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
    private val dictionaryRepository: DictionaryRepository
    ): ViewModel (){

    val userPreference = settingsRepository.userSettings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Setting()
        )

    private val _showAllCachesDeletedMessage = MutableSharedFlow<Unit>()
    val showAllCachesDeletedMessage = _showAllCachesDeletedMessage.asSharedFlow()

    private val _showAllSearchHistoryDeletedMessage = MutableSharedFlow<Unit>()
    val showAllSearchHistoryDeletedMessage = _showAllSearchHistoryDeletedMessage.asSharedFlow()

    fun setFontName(fontName: Font) {
        viewModelScope.launch {
            settingsRepository.setFont(fontName)
        }
    }

    fun setTheme(themeColor: Int) {
        viewModelScope.launch {
            settingsRepository.setTheme(themeColor)
        }
    }

    fun setShowExamples(setShowExamples: Boolean) {
        viewModelScope.launch {
            settingsRepository.setShowExamples(setShowExamples)
        }
    }

    fun setShowSynonyms(showSynonyms: Boolean) {
        viewModelScope.launch {
            settingsRepository.setShowSynonyms(showSynonyms)
        }
    }

    fun setShowAntonyms(showAntonyms: Boolean) {
        viewModelScope.launch {
            settingsRepository.setShowAntonyms(showAntonyms)
        }
    }

    fun deleteCaches() {
        viewModelScope.launch {
            dictionaryRepository.deleteAllWordsAndRemoteQueries()
            _showAllCachesDeletedMessage.emit(Unit)
        }
    }

    fun deleteSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.deleteAllSearches()
            _showAllSearchHistoryDeletedMessage.emit(Unit)
        }
    }
}