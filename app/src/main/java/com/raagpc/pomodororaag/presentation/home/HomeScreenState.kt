package com.raagpc.pomodororaag.presentation.home

data class HomeScreenState (
    val isRunning: Boolean = false,
    val workingTime: Boolean = true,
    val timeElapsed: Float = 0f,
    val scheduledTime: Float = 1500f,
    val error: String? = null
)