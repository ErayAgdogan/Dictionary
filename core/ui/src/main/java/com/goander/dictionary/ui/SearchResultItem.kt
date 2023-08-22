package com.goander.dictionary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goander.dictionary.design.DictionaryIcon

@Composable
public fun SearchResultItem(modifier: Modifier = Modifier,
                            searchResult: String = "",
                            searchedText: String = "",
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

        EmphasizedText(
            modifier = Modifier
                .padding(PaddingValues(horizontal = 16.dp, vertical = 24.dp))
                .weight(1f)
                .wrapContentHeight()
                .fillMaxWidth(),
            text = searchResult,
            emphasizedPartOfText = searchedText,
            ignoreCaseOnHighLight = ignoreCaseOnHighLight
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

@Preview
@Composable
private fun SearchResultItemPreview() {
    SearchResultItem()
}