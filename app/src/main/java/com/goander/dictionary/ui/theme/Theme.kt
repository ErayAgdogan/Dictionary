package com.goander.dictionary.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goander.dictionary.model.Font
import com.goander.dictionary.theme.ThemeColorScheme


@Composable
fun DictionaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    fontFamily: FontFamily,
    color: Int,
    content: @Composable () -> Unit
) {

    Log.e("theme", "color: $color")
    val themeColorScheme = ThemeColorScheme(color)
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> themeColorScheme.DarkColors
        else -> themeColorScheme.LightColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.apply {
                statusBarColor = colorScheme.surface.toArgb()
                navigationBarColor = colorScheme.surface.toArgb()
            }
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getTypography(fontFamily = fontFamily),
        content = content
    )
}
@Composable
fun DictionaryTheme(viewModel: ThemeViewModel = hiltViewModel(), content: @Composable () -> Unit) {
    val preference by viewModel.userPreferences.collectAsStateWithLifecycle()

    DictionaryTheme(
        fontFamily = preference.font.asComposeFontFamily(),
        color = preference.theme,
        content = content
    )
}

