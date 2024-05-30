package com.raagpc.pomodororaag.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raagpc.pomodororaag.presentation.shared.PomodoroBackground
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme

@Composable
fun GlassContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
        Box(
            modifier = modifier
                .padding(28.dp)
                .border(1.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.3f)),


        ) {
            content()
        }

}

// Previews
@Preview("Glass Container Dark", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun GlassContainerLightPreview() {
    PomodoroRaagTheme {
        Surface {
            PomodoroBackground(isLightTheme = false, modifier = Modifier.fillMaxSize()) {
                GlassContainer(modifier = Modifier.fillMaxSize()) {
                    // Content
                }
            }
        }
    }
}

@Preview("Glass Container Light", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun GlassContainerDarkPreview() {
    PomodoroRaagTheme(darkTheme = true) {
        Surface {
            PomodoroBackground(isLightTheme = true, modifier = Modifier.fillMaxSize()) {
                GlassContainer(modifier = Modifier.fillMaxSize()) {
                    // Content
                }
            }
        }
    }
}