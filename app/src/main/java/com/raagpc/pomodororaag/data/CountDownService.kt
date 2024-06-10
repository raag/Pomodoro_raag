package com.raagpc.pomodororaag.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.raagpc.pomodororaag.PomodoroRaagApplication
import com.raagpc.pomodororaag.R
import com.raagpc.pomodororaag.presentation.MainActivity

class CountdownService : Service() {
    companion object {
        const val ACTION_SEND_NOTIFICATION = "action_send_notification"
    }

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.beep)
    }

    override fun onStartCommand(serviceIntent: Intent?, flags: Int, startId: Int): Int {
        when (serviceIntent?.action) {
            ACTION_SEND_NOTIFICATION -> {
                mediaPlayer.start()
                sendNotification()
            }
            else -> {
                Log.i("CountdownService", "onStartCommand: ${serviceIntent?.action}")

            }
        }
        return START_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun sendNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // open MainActivity when notification is clicked
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE )


        val notification = NotificationCompat.Builder(this, PomodoroRaagApplication.CHANNEL_ID)
            .setContentTitle("Countdown Finished")
            .setContentText("The countdown has completed.")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notification.flags = NotificationCompat.FLAG_AUTO_CANCEL
        notificationManager.notify(1, notification)
    }
}