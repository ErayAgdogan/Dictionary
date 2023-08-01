package com.goander.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goander.dictionary.model.Setting
import com.goander.dictionary.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {

}