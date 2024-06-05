package com.raagpc.pomodororaag.presentation.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
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
    fun initBroadcast(context: Context) {
        val serviceIntent = Intent(context, CountdownService::class.java).apply {
            putExtra(CountdownService.EXTRA_DURATION, _state.value.scheduledTime.toLong())
        }
        context.startService(serviceIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(
                broadcastReceiver,
                IntentFilter(CountdownService.BROADCAST_ACTION), Context.RECEIVER_NOT_EXPORTED
            )
        }
    }


    fun toggleTimer(context: Context) {
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
}