package com.raagpc.pomodororaag.data

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.raagpc.pomodororaag.R

class CountdownService : Service() {
    companion object {
        const val BROADCAST_ACTION = "com.raagpc.countdown"
        const val EXTRA_REMAINING_TIME = "remaining_time"
        const val EXTRA_DURATION = "duration"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_RESUME = "action_resume"
        const val ACTION_RESTART = "action_restart"
    }

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var intent: Intent
    private var startTime: Long = 0
    private var duration: Long = 0
    private var remainingTime: Long = 0
    private var isPaused: Boolean = false

    override fun onCreate() {
        super.onCreate()
        intent = Intent(BROADCAST_ACTION)
    }

    override fun onStartCommand(serviceIntent: Intent?, flags: Int, startId: Int): Int {
        when (serviceIntent?.action) {
            ACTION_PAUSE -> pauseCountdown()
            ACTION_RESUME -> resumeCountdown()
            ACTION_RESTART -> restartCountdown(serviceIntent.getLongExtra(EXTRA_DURATION, 0))
            else -> {
                duration = serviceIntent?.getLongExtra(EXTRA_DURATION, 0) ?: 0
                remainingTime = duration
            }
        }
        return START_STICKY
    }


    private fun pauseCountdown() {
        handler.removeCallbacks(updateTimeRunnable)
        isPaused = true
    }

    private fun resumeCountdown() {
        handler.postDelayed(updateTimeRunnable, 1000)
        isPaused = false
    }

    private fun restartCountdown(newDuration: Long) {
        handler.removeCallbacks(updateTimeRunnable)
        isPaused = true
        duration = newDuration
        remainingTime = duration
    }

    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            if (!isPaused) {
                remainingTime -= 1
                if (remainingTime > 0) {
                    intent.putExtra(EXTRA_REMAINING_TIME, remainingTime)
                    sendBroadcast(intent)
                    handler.postDelayed(this, 1000)
                } else {
                    intent.putExtra(EXTRA_REMAINING_TIME, 0)
                    sendBroadcast(intent)
                    sendNotification()
                    stopSelf()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateTimeRunnable)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun sendNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "countdown_channel")
            .setContentTitle("Countdown Finished")
            .setContentText("The countdown has completed.")
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(1, notification)
    }
}