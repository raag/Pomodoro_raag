package com.raagpc.pomodororaag.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raagpc.pomodororaag.presentation.shared.PomodoroBackground
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme

@Composable
fun Title(text: String) {
    Box(
        Modifier
            .offset(y = 70.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.White.copy(alpha = 0.6f))
            .background(Color.White.copy(alpha = 0.2f))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h6.copy(fontSize = 28.sp),
            color = MaterialTheme.colors.onSurface
        )
    }
}

// Previews
@Composable
@Preview("Title Light", showBackground = true)
fun TitlePreview() {
    PomodoroRaagTheme {
        PomodoroBackground(isLightTheme = true, modifier = Modifier.height(200.dp)) {
            Title("Pomodoro Raag")
        }
    }
}

@Composable
@Preview("Title Dark", showBackground = true)
fun TitlePreviewDark() {
    PomodoroRaagTheme(darkTheme = true) {
        PomodoroBackground(isLightTheme = false, modifier = Modifier.height(200.dp)) {
            Title("Pomodoro Raag")
        }
    }
}