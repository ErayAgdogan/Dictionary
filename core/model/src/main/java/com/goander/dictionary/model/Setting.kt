package com.goander.dictionary.model

data class Setting(
    val font: Font = Font.DEFAULT,
    val theme: Int = -14575885,
    val showExamples: Boolean = true,
    val showSynonyms: Boolean = true,
    val showAntonyms: Boolean = true
    ) {
}

enum class Font  {
    DEFAULT,
    SERIF,
    CURSIVE,
    MONOSPACE,
    SANS_SERIF,
}

