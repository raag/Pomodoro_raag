package com.raagpc.pomodororaag.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raagpc.pomodororaag.presentation.shared.PomodoroBackground
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PomodoroBackground(
            isLightTheme = MaterialTheme.colors.isLight,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                Modifier
                    .offset(y = 60.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.White.copy(alpha = 0.6f))
                    .background(Color.White.copy(alpha = 0.3f))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Focus", style = MaterialTheme.typography.h6, color = Color.White)
            }

            GlassContainer(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                    ) {
                    CircularProgress(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(150.dp),
                        remainingSeconds = 1000f,
                        scheduledTimeSeconds = 1500f,
                        strokeWidth = 10.dp,
                        textStyle = MaterialTheme.typography.h6
                    )
                }
            }

        }
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