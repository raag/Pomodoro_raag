package com.raagpc.pomodororaag.presentation.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.raagpc.pomodororaag.data.CountdownService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel  @Inject constructor(): ViewModel() {
    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> get() = _state

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val remainingTimeValue = intent?.getLongExtra(CountdownService.EXTRA_REMAINING_TIME, 0) ?: 0
            if (remainingTimeValue == 0L) {
                changeMode()
                return
            }
            _state.value = _state.value.copy(
                remainingTime = remainingTimeValue.toFloat()
            )
        }
    }


    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initBroadcastReceiver(context: Context) {
        Log.i("HomeScreenViewModel", "initBroadcast: Starting service")
        val serviceIntent = Intent(context, CountdownService::class.java).apply {
            putExtra(CountdownService.EXTRA_DURATION, _state.value.scheduledTime.toLong())
        }
        context.startForegroundService(serviceIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(
                broadcastReceiver,
                IntentFilter(CountdownService.BROADCAST_ACTION), Context.RECEIVER_NOT_EXPORTED
            )
        }
    }


    fun toggleTimer(context: Context) {

        if (_state.value.remainingTime == _state.value.scheduledTime) {
            restartTimer(context)
        }

        _state.value = _state.value.copy(
            isRunning = !_state.value.isRunning
        )

        if (_state.value.isRunning) {
            val serviceIntent = Intent(context, CountdownService::class.java).apply {
                action = CountdownService.ACTION_RESUME
            }
            context.startService(serviceIntent)
        } else {
            val serviceIntent = Intent(context, CountdownService::class.java).apply {
                action = CountdownService.ACTION_PAUSE
            }
            context.startService(serviceIntent)
        }

    }

    fun restartTimer(context: Context) {
        _state.value = _state.value.copy(
            isRunning = false,
            remainingTime = _state.value.scheduledTime
        )
        val serviceIntent = Intent(context, CountdownService::class.java).apply {
            action = CountdownService.ACTION_RESTART
            putExtra(CountdownService.EXTRA_DURATION, _state.value.scheduledTime.toLong())
        }

        context.startService(serviceIntent)
    }

    fun unregisterBroadcast(context: Context) {
        context.unregisterReceiver(broadcastReceiver)
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

}