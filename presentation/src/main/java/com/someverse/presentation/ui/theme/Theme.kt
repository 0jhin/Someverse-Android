package com.someverse.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
    darkColorScheme(
        primary = PrimaryPurple,
        onPrimary = White,
        onSecondary = White,
        onBackground = White,
        onSurface = White,
        onError = White,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = PrimaryPurple,
        background = White,
        onPrimary = White,
        onSecondary = White,
        onBackground = Black,
        onSurface = Black,
        onError = White,
    )

@Composable
fun SomeverseTheme(
    // Always use light theme regardless of system settings
    darkTheme: Boolean = false, // Parameter kept for compatibility
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled dynamic colors
    content: @Composable () -> Unit,
) {
    // Always use light theme
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SomeverseTypography,
        content = content,
    )
}
