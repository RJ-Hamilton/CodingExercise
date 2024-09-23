package com.hamilton.codingexercise.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = DeepPlum,
    background = White,
    onPrimary = White,
    onSurface = Black
)

private val DarkColorScheme = darkColorScheme(
    primary = DeepPlum,
    background = Black,
    onPrimary = White,
    onSurface = White
)

/**
 * Composable function to apply a custom theme for the CodingExercise app.
 *
 * This function applies a `MaterialTheme` to the content passed to it. The theme can dynamically
 * switch between a light or dark color scheme depending on the `darkTheme` parameter. The color
 * scheme and typography are customized based on the app's design requirements.
 *
 * @param darkTheme Boolean indicating whether to use the dark theme. Defaults to the system-wide
 * dark theme setting (`isSystemInDarkTheme()`).
 * @param content A `@Composable` lambda that represents the UI content to which the theme will be
 * applied.
 *
 * Example usage:
 * ```
 * CodingExerciseTheme {
 *     // Your UI content here
 *     MyComposableScreen()
 * }
 * ```
 */
@Composable
fun CodingExerciseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}