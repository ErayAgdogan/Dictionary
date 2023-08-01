package com.goander.dictionary.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun SettingAlertDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {


    AlertDialog(
        onDismissRequest = onDismissRequest,

    ) {
        Card {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
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

            content(this)

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
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = stringResource(id = R.string.dismiss),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                TextButton(onClick = {
                    onConfirm()
                    onDismissRequest()
                }) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

            }
        }
    }
}


@Preview
@Composable
private fun SettingAlertDialogPreview() {
    SettingAlertDialog(
        title = "Title",
        onDismissRequest = { },
        onConfirm = {  }) {
        
    }
}
