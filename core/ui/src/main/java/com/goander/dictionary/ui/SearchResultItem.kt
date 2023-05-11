package com.goander.dictionary.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.goander.dictionary.design.DictionaryIcon
import java.util.regex.Pattern

@Composable
public fun SearchResultItem(modifier: Modifier,
                            searchResult: String,
                            searchedText: String,
                            onDelete: (() -> Unit)? = null,
                            ignoreCaseOnHighLight: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.padding(start = 16.dp),
            painter = painterResource(id = DictionaryIcon.Search),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(PaddingValues(horizontal = 16.dp, vertical = 24.dp))
                .weight(1f),
            text = buildAnnotatedString {

                val textList =  Pattern.compile(
                    searchedText,
                    if (ignoreCaseOnHighLight) Pattern.CASE_INSENSITIVE else 0
                ).split(searchResult)

                for ((i, text) in  textList.withIndex()) {
                    append(text)
                    if (i != textList.lastIndex || searchResult.endsWith(searchedText))
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(searchedText)
                        }
                }

            },
        )
        IconButton(onClick = { onDelete?.invoke() }) {
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                painter = painterResource(id = DictionaryIcon.Clear),
                contentDescription = ""
            )
        }


    }
}