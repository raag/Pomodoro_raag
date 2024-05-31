package com.raagpc.pomodororaag.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raagpc.pomodororaag.R
import com.raagpc.pomodororaag.presentation.shared.PomodoroBackground
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme

@Composable
fun Controls(
    isRunning: Boolean = false,
    onToggleTimer: () -> Unit = {},
    onRestartTimer: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        if (isRunning) {
            Box(
                modifier = Modifier
                    .offset(x = (80).dp)
                    .clickable { }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_restart),
                    contentDescription = "Stop",
                    modifier = Modifier
                        .width(50.dp)
                        .clickable { })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val buttonImage = if (isRunning) {
                R.drawable.ic_pause
            } else {
                R.drawable.ic_play
            }
            Box {
                Image(
                    painter = painterResource(id = buttonImage),
                    contentDescription = "Play",
                    modifier = Modifier
                        .width(80.dp)
                        .clickable { })
            }
        }
    }


}

// Previews
@Preview("Controls Dark", showBackground = true, widthDp = 360, heightDp = 60)
@Composable
fun ControlsLightPreview() {
    PomodoroRaagTheme {
        Surface {
            PomodoroBackground(isLightTheme = false, modifier = Modifier.fillMaxSize()) {
                Controls()
            }
        }
    }
}

@Preview("Controls Light", showBackground = true, widthDp = 360, heightDp = 60)
@Composable
fun ControlsDarkPreview() {
    PomodoroRaagTheme(darkTheme = true) {
        Surface {
            PomodoroBackground(isLightTheme = true, modifier = Modifier.fillMaxSize()) {
                Controls()
            }
        }
    }
}


@Preview("Controls Dark", showBackground = true, widthDp = 360, heightDp = 60)
@Composable
fun ControlsRunningLightPreview() {
    PomodoroRaagTheme {
        Surface {
            PomodoroBackground(isLightTheme = false, modifier = Modifier.fillMaxSize()) {
                Controls(isRunning = true)
            }
        }
    }
}

@Preview("Controls Light", showBackground = true, widthDp = 360, heightDp = 60)
@Composable
fun ControlsRunningDarkPreview() {
    PomodoroRaagTheme(darkTheme = true) {
        Surface {
            PomodoroBackground(isLightTheme = true, modifier = Modifier.fillMaxSize()) {
                Controls(isRunning = true)
            }
        }
    }
}