package com.goander.dictionary.settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview


@Composable
public fun MessageDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton =  {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text(
                    text = stringResource(id = R.string.confirm),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text(
                    text = stringResource(id = R.string.dismiss),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

    )
}

@Preview
@Composable
private fun MessageDialogPreview() {
    MessageDialog(title = "title", message = "message")
}
