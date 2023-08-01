package com.goander.dictionary.bookmark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.goander.dictionary.ui.EmphasizedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun BookmarkedWordsScreen(
    navigateToSearchScreen: (String) -> Unit = { },
    navigateToBack: () -> Unit = {},
    viewModel: BookmarkedWordsViewModel = viewModel(),
) {
    val query by viewModel.searchStateFlow.collectAsStateWithLifecycle()
    val bookmarkedWords = viewModel.bookmarkedWordsPagingData.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            Column {
                SearchBar(
                    modifier = Modifier.padding(16.dp),
                    query = query,
                    onQueryChange = viewModel::search,
                    onSearch = {} ,
                    active = false,
                    onActiveChange = { },
                    leadingIcon = {
                        IconButton(onClick = navigateToBack) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "")
                        }

                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Filled.Search,
                            contentDescription = "")
                    }
                ) {

                }
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp))
            }

        }
    ) {
       LazyColumn (modifier = Modifier.padding(it)){

           items(
               count = bookmarkedWords.itemCount,
               key = { bookmarkedWords[it]?.word?:"" }
           ) {
               val word = bookmarkedWords[it]?.word?:""

               when(val item = bookmarkedWords[it]) {
                   is BookmarkedWordItem.WordWithAlphabet ->
                       Text(
                           modifier = Modifier
                               .padding(horizontal = 16.dp, vertical = 32.dp)
                               .wrapContentHeight(),
                           text = item.alphabet.toString(),
                           style = MaterialTheme.typography.titleLarge,
                           color = MaterialTheme.colorScheme.primary
                       )
                   is BookmarkedWordItem.Word -> {
                     //  Divider(modifier = Modifier.fillMaxWidth().height(1.dp).padding(horizontal = 16.dp))
                   }
                   null -> {}
               }


               EmphasizedText(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(60.dp)
                       .clickable { navigateToSearchScreen(word) }
                       .padding(16.dp)
                       .wrapContentHeight(),
                   text = word,
                   emphasizedPartOfText = query,
               )

           }
       }
    }
}

