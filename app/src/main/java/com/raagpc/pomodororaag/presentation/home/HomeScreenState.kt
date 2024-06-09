package com.raagpc.pomodororaag.presentation.home

data class HomeScreenState (
    val isRunning: Boolean = false,
    val workingTime: Boolean = true,
    val remainingTime: Long = HomeScreenTimes.WORKING_TIME,
    val scheduledTime: Long = HomeScreenTimes.WORKING_TIME,
    val error: String? = null,
    val completedLoops: Int = 0
)

object HomeScreenTimes {
    const val WORKING_TIME = 1500L
    const val REST_TIME = 300L
    const val LONG_REST_TIME = 900L
}