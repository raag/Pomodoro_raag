package com.raagpc.pomodororaag.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raagpc.pomodororaag.R
import com.raagpc.pomodororaag.presentation.shared.PomodoroBackground
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme

@Composable
fun HomeScreen(
    working: Boolean = true,
    isRunning: Boolean = false,
    timeElapsed: Float = 0f,
    scheduledTime: Float = 1500f,
    onToggleTimer: () -> Unit = {},
    onRestartTimer: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PomodoroBackground(
            isLightTheme = MaterialTheme.colors.isLight,
            working = working,
            modifier = Modifier.fillMaxSize()
        ) {

            val message = if (working) {
                R.string.keep_focus_message
            } else {
                R.string.break_message
            }
            Title(text = stringResource(id = message))

            val color = if (working) {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.secondary
            }

            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp

            GlassContainer(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    CircularProgress(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(screenWidth * 0.5f),
                        timeElapsed = timeElapsed,
                        scheduledTime = scheduledTime,
                        strokeWidth = (screenWidth.value / 30).dp,
                        textStyle = MaterialTheme.typography.h6.copy(fontSize = (screenWidth.value / 15).sp),
                        color = color
                    )
                    Spacer(modifier = Modifier.height(60.dp))

                    Controls(
                        isRunning = isRunning,
                        onToggleTimer = onToggleTimer,
                        onRestartTimer = onRestartTimer
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
        HomeScreen(
            working = true,
            timeElapsed = 1000f,
            scheduledTime = 1500f,
            isRunning = true
        )
    }
}

@Composable
@Preview("Home preview dark", showBackground = true, widthDp = 360, heightDp = 640)
fun HomePreviewDark() {
    PomodoroRaagTheme(darkTheme = true) {
        HomeScreen(
            working = true,
            timeElapsed = 1000f,
            scheduledTime = 1500f,
            isRunning = true
        )
    }
}


@Composable
@Preview("Home preview rest", showBackground = true, widthDp = 360, heightDp = 640)
fun HomePreviewRest() {
    PomodoroRaagTheme {
        HomeScreen(
            working = false,
            timeElapsed = 0f,
            scheduledTime = 250f,
        )
    }
}

@Composable
@Preview("Home preview rest dark", showBackground = true, widthDp = 360, heightDp = 640)
fun HomePreviewRestDark() {
    PomodoroRaagTheme(darkTheme = true) {
        HomeScreen(
            working = false,
            timeElapsed = 0f,
            scheduledTime = 250f,
        )
    }
}
