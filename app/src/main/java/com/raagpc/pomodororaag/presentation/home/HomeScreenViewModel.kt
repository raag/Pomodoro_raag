package com.raagpc.pomodororaag.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel  @Inject constructor(): ViewModel() {
    private val _state = mutableStateOf(HomeScreenState())

    val state: State<HomeScreenState> get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message
        )
    }

    fun toggleTimer() {
        _state.value = _state.value.copy(
            isRunning = !_state.value.isRunning
        )
    }

    fun restartTimer() {
        _state.value = _state.value.copy(
            isRunning = false,
            timeElapsed = 0f
        )
    }

}