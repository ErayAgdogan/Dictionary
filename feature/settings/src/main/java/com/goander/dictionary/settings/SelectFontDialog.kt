package com.goander.dictionary.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.goander.dictionary.model.Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun SelectFontDialog(
    selectedFont: Font,
    onFontSelected: (Font) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var preSelectedFont: Font by rememberSaveable {
        mutableStateOf(selectedFont)
    }
// Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    SettingAlertDialog(
        title = stringResource(id = R.string.typography),
        onDismissRequest = onDismissRequest,
        onConfirm = { onFontSelected(preSelectedFont) }) {
        LazyColumn(
            modifier = Modifier.selectableGroup(),
        ) {

            items(fontStringResourceIdMap.keys.toList()) { font ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (font == preSelectedFont),
                            onClick = { preSelectedFont = font },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    RadioButton(
                        selected = (font == preSelectedFont),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(id = fontStringResourceIdMap[font]!!),
                        style = MaterialTheme.typography.bodySmall.merge(),
                        fontFamily = fontToComposeFontFamily[font]
                    )
                }
            }
        }

    }

}