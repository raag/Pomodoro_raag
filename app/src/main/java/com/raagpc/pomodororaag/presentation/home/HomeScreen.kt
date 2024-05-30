package com.raagpc.pomodororaag.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text("Hello, World!")
    }
}

@Composable
@Preview("Home preview", showBackground = true, widthDp = 360, heightDp = 640)
fun HomePreview() {
    PomodoroRaagTheme {
        HomeScreen()
    }
}

@Composable
@Preview("Home preview dark", showBackground = true, widthDp = 360, heightDp = 640)
fun HomePreviewDark() {
    PomodoroRaagTheme(darkTheme = true) {
        HomeScreen()
    }
}