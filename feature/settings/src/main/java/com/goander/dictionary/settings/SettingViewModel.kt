package com.goander.dictionary.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle
    ): ViewModel (){

    val fontName: StateFlow<String> = savedStateHandle.getStateFlow("FontName", "Serif")

    fun setFontName(fontName: String) {
        savedStateHandle["FontName"] = fontName
    }
}