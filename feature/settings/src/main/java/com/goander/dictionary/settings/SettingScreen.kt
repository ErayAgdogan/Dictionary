package com.goander.dictionary.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goander.dictionary.design.DictionaryIcon
import com.goander.dictionary.design.DictionaryString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun SettingScreen(viewModel: SettingViewModel = viewModel()) {
    val fontName by viewModel.fontName.collectAsState()
    var openSelectFontDialog by remember { mutableStateOf(false) }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                SettingCategory(name = stringResource(id = DictionaryString.Theme))
            }
            item {
                Box (modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        openSelectFontDialog = true
                    }) {
                    Text(text = stringResource(id = DictionaryString.Typography))
                }
            }
        }

    }
    if (openSelectFontDialog)
        SelectFontDialog (
            fontName = fontName,
            onFontSelected = viewModel::setFontName,
            onDismissRequest = {
            openSelectFontDialog = false
        })
}

@Composable
public fun SettingCategory(name: String) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = name,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Divider(modifier = Modifier
            .weight(1f)
            .height(1.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun SelectFontDialog(fontName:String, onFontSelected: (String) -> Unit, onDismissRequest: () -> Unit, ) {
    val radioOptions = stringArrayResource(id = DictionaryString.Typographies)

// Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    AlertDialog(onDismissRequest = onDismissRequest) {
        Card {
            Box(modifier = Modifier
                .height(56.dp)
                .padding(horizontal = 16.dp),) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = stringResource(id = DictionaryString.Typography),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            LazyColumn(
                modifier = Modifier.selectableGroup(),
            ) {

                items(radioOptions) { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == fontName),
                                onClick = { onFontSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == fontName),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = DictionaryString.Cancel),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(id = DictionaryString.Select),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }

    }
}

@Preview
@Composable
public fun SettingScreenPreview() {
    SettingScreen()
}