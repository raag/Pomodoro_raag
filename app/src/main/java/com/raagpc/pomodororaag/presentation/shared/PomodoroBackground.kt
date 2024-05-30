package com.raagpc.pomodororaag.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.raagpc.pomodororaag.R
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme


@Composable
fun PomodoroBackground(working: Boolean = true, isLightTheme: Boolean, modifier: Modifier, content: @Composable () -> Unit) {
    val backgroundImage = if (isLightTheme) {
        if (working) {
            R.mipmap.bg_1_light
        } else {
            R.mipmap.bg_2_light
        }
    } else {
        if (working) {
            R.mipmap.bg_1_dark
        } else {
            R.mipmap.bg_2_dark
        }
    }
    val backgroundBrush =
        ShaderBrush(ImageShader(ImageBitmap.imageResource(id = backgroundImage)))

    Box(modifier = modifier
        .background(backgroundBrush)) {
        content()
    }
}


// Previews
@Preview("Background working Dark", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun BackgroundPreview() {
    PomodoroRaagTheme(darkTheme = true) {
        PomodoroBackground(isLightTheme = false, modifier = Modifier.fillMaxSize()) {
            // Content
        }
    }
}

@Preview("Background working Light", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun BackgroundPreviewDark() {
    PomodoroRaagTheme(darkTheme = false) {
        PomodoroBackground(isLightTheme = true, modifier = Modifier.fillMaxSize()) {
            // Content
        }
    }
}


@Preview("Background resting Dark", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun BackgroundRestingPreview() {
    PomodoroRaagTheme(darkTheme = true) {
        PomodoroBackground(working = false, isLightTheme = false, modifier = Modifier.fillMaxSize()) {
            // Content
        }
    }
}

@Preview("Background resting Light", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun BackgroundRestingPreviewDark() {
    PomodoroRaagTheme(darkTheme = false) {
        PomodoroBackground(working = false, isLightTheme = true, modifier = Modifier.fillMaxSize()) {
            // Content
        }
    }
}