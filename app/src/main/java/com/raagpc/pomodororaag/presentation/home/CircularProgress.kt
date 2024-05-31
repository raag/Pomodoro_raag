package com.raagpc.pomodororaag.presentation.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.StrokeCap
import java.util.Locale

@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    remainingSeconds: Float,
    scheduledTimeSeconds: Float = 1500f, // 25 minutes
    textStyle: TextStyle = MaterialTheme.typography.subtitle2,
    strokeWidth: Dp = 6.dp
) {

    val hours = (remainingSeconds / 3600).toInt()
    val minutes = ((remainingSeconds % 3600) / 60).toInt()
    val seconds = (remainingSeconds % 60).toInt()

    val timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)

    val indicatorProgress = remainingSeconds / scheduledTimeSeconds
    var progress by remember { mutableFloatStateOf(0f) }
    val progressAnimDuration = 150
    val progressAnimation by animateFloatAsState(
        targetValue = indicatorProgress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
        label = ""
    )
    Box {
        CircularProgressIndicator(
            modifier = modifier,
            progress = progressAnimation,
            strokeWidth = strokeWidth,
            color = MaterialTheme.colors.primary,
            strokeCap = StrokeCap.Butt,
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        )
        Text(
            text = timeString, textAlign = TextAlign.Center, modifier = Modifier.align(
                Alignment.Center
            ),
            style = textStyle,
            color = MaterialTheme.colors.onSurface
        )
    }
    LaunchedEffect(indicatorProgress) {
        progress = indicatorProgress
    }
}


@Preview(showBackground = true)
@Composable
fun CircularProgressPreview() {
    PomodoroRaagTheme {
        Surface {
            CircularProgress(
                remainingSeconds = 1000f,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularProgressDarkPreview() {
    PomodoroRaagTheme(darkTheme = true) {
        Surface {
            CircularProgress(
                remainingSeconds = 1000f,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}