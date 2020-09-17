package com.raagpc.pomodororaag

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(private val context: Context): ViewModel() {
    enum class UIStatus {
        LOADING,
        READY_TO_WORK,
        READY_TO_BREAK,
        RUNNING_WORK,
        RUNNING_BREAK,
        PAUSE_WORK,
        PAUSE_BREAK
    }

    private var loops = 0
    val status = MutableLiveData<UIStatus>()
    private lateinit var player: MediaPlayer

    init {
        status.value = UIStatus.LOADING
        setupPlayer()
    }

    private fun setupPlayer() {
        player = MediaPlayer.create(context, R.raw.beep)
        player.setOnPreparedListener {
            status.postValue(UIStatus.READY_TO_WORK)
        }
    }

    fun toggleTimer() {
        when (status.value) {
            UIStatus.READY_TO_WORK, UIStatus.READY_TO_BREAK -> startTimer()
            UIStatus.PAUSE_BREAK, UIStatus.PAUSE_WORK -> startTimer()
            UIStatus.RUNNING_WORK, UIStatus.RUNNING_BREAK -> pauseTimer()
            else -> {}
        }
    }

    fun onTimerFinish() {
        player.start()
        when (status.value) {
            UIStatus.RUNNING_WORK -> {
                loops ++
                status.value = UIStatus.READY_TO_BREAK
            }
            UIStatus.RUNNING_BREAK -> status.value = UIStatus.READY_TO_WORK
            else -> {}
        }
    }

    fun resetTimer() {
        loops = 0
        status.value = UIStatus.READY_TO_WORK
    }

    fun getTimeToCount(): Int {
        return when (status.value) {
            UIStatus.READY_TO_WORK -> WORK_TIME
            UIStatus.READY_TO_BREAK -> getBreakTime()
            else -> 0
        }
    }

    private fun getBreakTime(): Int {
        return if (loops % LOOPS_TO_LONG_BREAK == 0) {
            LONG_BREAK_TIME
        } else {
            NORMAL_BREAK_TIME
        }
    }

    private fun startTimer() {
        when (status.value) {
            UIStatus.READY_TO_WORK,
                UIStatus.PAUSE_WORK -> status.value = UIStatus.RUNNING_WORK
            UIStatus.READY_TO_BREAK,
                UIStatus.PAUSE_BREAK -> status.value = UIStatus.RUNNING_BREAK
            else -> {}
        }
    }

    private fun pauseTimer() {
        when (status.value) {
            UIStatus.RUNNING_WORK -> status.value = UIStatus.PAUSE_WORK
            UIStatus.RUNNING_BREAK -> status.value = UIStatus.PAUSE_BREAK
            else -> {}
        }
    }

    companion object {
        const val WORK_TIME = 1500
        const val NORMAL_BREAK_TIME = 300
        const val LONG_BREAK_TIME = 900
        const val LOOPS_TO_LONG_BREAK = 3
    }
}