package com.raagpc.pomodororaag.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White


private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    primaryVariant = Accent,
    secondary = SecondaryDark,
    onSurface = White
)

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = Accent,
    secondary = Secondary,
    onSurface = White

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)


@Composable
fun PomodoroRaagTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(colors = colors, content = content)
}
