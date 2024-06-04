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
    state: HomeScreenState,
    onToggleTimer: () -> Unit = {},
    onRestartTimer: () -> Unit = {}
) {

    val workingTime: Boolean = state.workingTime
    val isRunning: Boolean = state.isRunning
    val timeElapsed: Float = state.timeElapsed
    val scheduledTime: Float = state.scheduledTime

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PomodoroBackground(
            isLightTheme = MaterialTheme.colors.isLight,
            working = workingTime,
            modifier = Modifier.fillMaxSize()
        ) {

            val message = if (workingTime) {
                R.string.keep_focus_message
            } else {
                R.string.break_message
            }

            val color = if (workingTime) {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.secondary
            }

            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp

            Title(text = stringResource(id = message))

            GlassContainer(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    ) {
                    Spacer(modifier = Modifier.height(30.dp)) // Space to avoid overlapping with the status bar
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
            HomeScreenState(
                workingTime = true,
                timeElapsed = 1000f,
                scheduledTime = 1500f,
                isRunning = true
            )
        )
    }
}

@Composable
@Preview("Home preview dark", showBackground = true, widthDp = 360, heightDp = 640)
fun HomePreviewDark() {
    PomodoroRaagTheme(darkTheme = true) {
        HomeScreen(
            HomeScreenState(
                workingTime = true,
                timeElapsed = 1000f,
                scheduledTime = 1500f,
                isRunning = true
            )
        )
    }
}


@Composable
@Preview("Home preview rest", showBackground = true, widthDp = 360, heightDp = 640)
fun HomePreviewRest() {
    PomodoroRaagTheme {
        HomeScreen(
            HomeScreenState(
                workingTime = false,
                timeElapsed = 0f,
                scheduledTime = 250f,
            )
        )
    }
}

@Composable
@Preview("Home preview rest dark", showBackground = true, widthDp = 360, heightDp = 640)
fun HomePreviewRestDark() {
    PomodoroRaagTheme(darkTheme = true) {
        HomeScreen(
            HomeScreenState(
                workingTime = false,
                timeElapsed = 0f,
                scheduledTime = 250f
            )
        )
    }
}
