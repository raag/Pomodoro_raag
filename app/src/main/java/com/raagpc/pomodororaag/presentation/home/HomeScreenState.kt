package com.raagpc.pomodororaag.presentation.home

data class HomeScreenState (
    val isRunning: Boolean = false,
    val workingTime: Boolean = true,
    val remainingTime: Float = HomeScreenTimes.WORKING_TIME,
    val scheduledTime: Float = HomeScreenTimes.WORKING_TIME,
    val error: String? = null,
    val completedLoops: Int = 0
)

object HomeScreenTimes {
    const val WORKING_TIME = 1500f
    const val REST_TIME = 300f
    const val LONG_REST_TIME = 900f
}