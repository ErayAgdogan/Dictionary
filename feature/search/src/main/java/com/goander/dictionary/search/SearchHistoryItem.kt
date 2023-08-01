package com.goander.dictionary.search

import com.goander.dictionary.model.SearchHistory

sealed interface SearchHistoryItem {
    public data class ItemSearchHistory(val searchId: Long, val search: String): SearchHistoryItem
}

public fun SearchHistory.asItem(): SearchHistoryItem =
    SearchHistoryItem.ItemSearchHistory(
        searchId = id,
        search = search
    )