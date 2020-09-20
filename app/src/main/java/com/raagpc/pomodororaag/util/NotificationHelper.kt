package com.raagpc.pomodororaag.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import com.raagpc.pomodororaag.R


object NotificationHelper {
    private const val CHANNEL_ID = "Pomodoro"
    private const val notificationId = 0

    fun sendNotification(context: Context, @StringRes message: Int) {
        val notificationBuilder = this.buildNotification(context, message)
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
            notificationBuilder.setChannelId(CHANNEL_ID)
        }

        mNotificationManager.notify(notificationId, notificationBuilder.build());
    }

    private fun buildNotification(context: Context, @StringRes message: Int): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_play)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(message))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(context.getString(message))
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}