package com.raagpc.pomodororaag.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.raagpc.pomodororaag.data.CountdownService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel  @Inject constructor(): ViewModel() {
    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> get() = _state

    private lateinit var countDownTimer: CountDownTimer
    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context


    fun setContext(context: Context) {
        this.context = context
    }


    fun toggleTimer() {
        if (_state.value.remainingTime == _state.value.scheduledTime) {
            restartTimer()
        }

        _state.value = _state.value.copy(
            isRunning = !_state.value.isRunning
        )

        if (_state.value.isRunning) {
            countDownTimer.start()
        } else {
            countDownTimer.cancel()
        }

    }

    fun restartTimer() {
        _state.value = _state.value.copy(
            isRunning = false,
            remainingTime = _state.value.scheduledTime
        )

        countDownTimer = object : CountDownTimer(_state.value.scheduledTime * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("HomeScreenViewModel", "onTick: ${millisUntilFinished / 1000}")
                _state.value = _state.value.copy(
                    remainingTime = millisUntilFinished / 1000
                )
            }

            override fun onFinish() {
               _state.value = _state.value.copy(
                    isRunning = false
                )
                sendNotification()
                changeMode()
            }
        }
    }



    private fun changeMode() {
        if (_state.value.workingTime) {
            val time = if ((_state.value.completedLoops + 1) % 3 == 0) {
                HomeScreenTimes.LONG_REST_TIME
            } else {
                HomeScreenTimes.REST_TIME
            }
            _state.value = _state.value.copy(
                isRunning = false,
                remainingTime = time,
                scheduledTime = time,
                workingTime = false,
                completedLoops = _state.value.completedLoops + 1
            )
        } else {
            _state.value = _state.value.copy(
                isRunning = false,
                remainingTime = HomeScreenTimes.WORKING_TIME,
                scheduledTime = HomeScreenTimes.WORKING_TIME,
                workingTime = true,
            )
        }
    }

    fun sendNotification() {
        context.let {
            val serviceIntent = Intent(it, CountdownService::class.java).apply {
                action = CountdownService.ACTION_SEND_NOTIFICATION
            }
            it.startService(serviceIntent)
        }
    }

}