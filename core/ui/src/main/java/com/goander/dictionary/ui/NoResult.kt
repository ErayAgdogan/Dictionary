package com.goander.dictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goander.dictionary.design.DictionaryIcon

@Composable
public fun NoResult(modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        Row(modifier = Modifier
            .wrapContentSize()
            .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                painter = painterResource(id = DictionaryIcon.SearchOff),
                contentDescription = stringResource(id = R.string.no_result)
            )
            Text(
                text = stringResource(id = R.string.no_result),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

    }
}