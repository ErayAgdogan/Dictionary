package com.goander.dictionary.bookmark

sealed interface BookmarkedWordItem {
    val word: String
    public data class Word(override val word: String): BookmarkedWordItem
    public data class WordWithAlphabet(val alphabet: Char, override val word: String): BookmarkedWordItem
}