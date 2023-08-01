package com.goander.dictionary.settings

import androidx.compose.ui.text.font.FontFamily
import com.goander.dictionary.model.Font

val fontStringResourceIdMap: Map<Font, Int> = mapOf(
    Font.DEFAULT to R.string.Default,
    Font.SERIF to R.string.serif,
    Font.CURSIVE to R.string.cursive,
    Font.MONOSPACE to R.string.monospace,
    Font.SANS_SERIF to R.string.sans_serif
)

val fontToComposeFontFamily: Map<Font, FontFamily> = mapOf<Font, FontFamily>(
    Font.DEFAULT to FontFamily.Default,
    Font.SERIF to FontFamily.Serif,
    Font.CURSIVE to FontFamily.Cursive,
    Font.MONOSPACE to FontFamily.Monospace,
    Font.SANS_SERIF to FontFamily.SansSerif
)