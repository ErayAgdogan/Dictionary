package com.goander.dictionary.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goander.dictionary.model.Font
import com.goander.dictionary.theme.ColorBlue
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun SettingScreen(
    navigateToBack: () -> Unit,
    viewModel: SettingViewModel = viewModel()
) {

    val userSettings by viewModel.userPreference.collectAsState()

    SettingScreen(
        showExamples = userSettings.showExamples,
        onSetShowExamples = viewModel::setShowExamples,
        showSynonyms = userSettings.showSynonyms,
        onSetShowSynonyms = viewModel::setShowSynonyms,
        showAntonyms =  userSettings.showAntonyms,
        onSetShowAntonyms = viewModel::setShowAntonyms,
        font = userSettings.font,
        onSetFontName = viewModel::setFontName,
        theme = userSettings.theme,
        onSetTheme = viewModel::setTheme,
        onDeleteSearchHistory = viewModel::deleteSearchHistory,
        onDeleteCaches = viewModel::deleteCaches,
        showMessageAllCachesDeleted = viewModel.showAllCachesDeletedMessage,
        showMessageAllSearchHistoryDeleted = viewModel.showAllSearchHistoryDeletedMessage,
        navigateToBack = navigateToBack

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun SettingScreen(
    showExamples: Boolean = true,
    onSetShowExamples: (Boolean) -> Unit = { },
    showSynonyms: Boolean = true,
    onSetShowSynonyms: (Boolean) -> Unit = { },
    showAntonyms: Boolean = true,
    onSetShowAntonyms: (Boolean) -> Unit = { },
    font: Font = Font.DEFAULT,
    onSetFontName: (Font) -> Unit = {  },
    theme: Int = ColorBlue.seed.toArgb(),
    onSetTheme: (Int) -> Unit = { },
    onDeleteSearchHistory: () -> Unit = {},
    onDeleteCaches: () -> Unit = { },
    showMessageAllCachesDeleted: SharedFlow<Unit> = MutableSharedFlow(),
    showMessageAllSearchHistoryDeleted : SharedFlow<Unit> = MutableSharedFlow(),
    navigateToBack: () -> Unit = {  }
    ) {

    var openSelectFontDialog by rememberSaveable { mutableStateOf(false) }
    var openSelectThemeDialog by rememberSaveable { mutableStateOf(false) }
    var openDeleteSearchHistoryDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var openDeleteCachesDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val messageAllCachesDeleted = stringResource(id = R.string.all_caches_deleted)
    val messageAllSearchHistoryDeleted = stringResource(id = R.string.all_search_history_deleted)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = snackbarHostState) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            launch {
                showMessageAllCachesDeleted.collectLatest{
                    snackbarHostState.showSnackbar(message = messageAllCachesDeleted)
                }
            }

            showMessageAllSearchHistoryDeleted.collectLatest {
                launch {
                    snackbarHostState.showSnackbar(message = messageAllSearchHistoryDeleted)
                }

            }
        }
       
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            SettingsTopBar(navigateToBack = navigateToBack)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
        ) {
            item { 
                SettingCategory(name = stringResource(id = R.string.dictionary))
            }
            item {
                SettingSwitchItem(
                    settingTitle = stringResource(id = R.string.show_examples),
                    checked = showExamples,
                    onCheckedChange = onSetShowExamples
                )
            }

            item {
                SettingSwitchItem(
                    settingTitle = stringResource(id = R.string.show_synonyms),
                    checked = showSynonyms,
                    onCheckedChange = onSetShowSynonyms
                )
            }

            item {
                SettingSwitchItem(
                    settingTitle = stringResource(id = R.string.show_antonyms),
                    checked = showAntonyms,
                    onCheckedChange = onSetShowAntonyms)
            }

            item {
                SettingCategory(
                    name = stringResource(id = R.string.theme)
                )
            }
            item {
                SettingItem(
                    settingTitle = stringResource(id = R.string.typography),
                    onClick = {  openSelectFontDialog = true }) {
                    Text(
                        text = stringResource(id = getFontNameStringID(font)),
                    )
                }

            }

            item {
                SettingItem(
                    settingTitle =  stringResource(id = R.string.color),
                    onClick = { openSelectThemeDialog = true }) {
                    Card(
                        modifier = Modifier.size(36.dp),
                        onClick = {},
                        colors = CardDefaults.cardColors(containerColor = Color(theme)),
                        shape = RoundedCornerShape(4.dp),

                        ) {

                    }
                }
            }
            
            item {
                SettingCategory(name = stringResource(id = R.string.history_and_caching))
            }
           
            item { 
                SettingItem(
                    settingTitle = stringResource(id = R.string.delete_search_history),
                    onClick = { openDeleteSearchHistoryDialog = true }
                ) {
                }
            }
            
            item { 
                SettingItem(
                    settingTitle = stringResource(id = R.string.delete_caches),
                    onClick = { openDeleteCachesDialog = true }) {
                    
                }
            }
        }

    }

    if (openSelectFontDialog)
        SelectFontDialog (
            selectedFont = font,
            onFontSelected = onSetFontName,
            onDismissRequest = {
                openSelectFontDialog = false
            })


    if (openSelectThemeDialog)
        SelectColorDialog(
            selectedTheme = theme,
            onThemeSelected = onSetTheme,
            onDismissRequest = {
                openSelectThemeDialog = false
            }
        )

    if (openDeleteSearchHistoryDialog)
        MessageDialog(
            title = stringResource(id = R.string.delete_search_history),
            message = stringResource(id = R.string.delete_search_history_message),
            onDismiss =  { openDeleteSearchHistoryDialog = false },
            onConfirm = { onDeleteSearchHistory() }
            )

    if (openDeleteCachesDialog) 
        MessageDialog(
            title = stringResource(id = R.string.delete_caches),
            message = stringResource(id = R.string.delete_caches_message),
            onDismiss = { openDeleteCachesDialog = false },
            onConfirm = { onDeleteCaches() }
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsTopBar(navigateToBack: () -> Unit) {

    Column(modifier = Modifier
        .height(72.dp)
        .fillMaxWidth()) {
        TopAppBar(
            title = {
            Text(stringResource(id = R.string.settings))
        },
            navigationIcon = {
                IconButton(onClick = navigateToBack) {
                    Icon(Icons.Filled.ArrowBack,
                        contentDescription =
                        stringResource(id = R.string.back_to_main_screen))
                }
            }
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
    }
}

private fun getFontNameStringID(font: Font) =  fontStringResourceIdMap[font]!!

@Composable
public fun SettingCategory(name: String) {
    Row (
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(72.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

    }
}

@Composable
public fun SettingItem(settingTitle: String, onClick: () -> Unit, settingDisplayContent: @Composable RowScope.() -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = settingTitle)
        Spacer(modifier = Modifier.weight(1f))
        settingDisplayContent(this)
    }
}

@Composable
public fun SettingSwitchItem(
    settingTitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit) {
    SettingItem(
        settingTitle = settingTitle,
        onClick = { onCheckedChange(!checked) }
    ) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview
@Composable
public fun SettingScreenPreview() {
    SettingScreen()
}

