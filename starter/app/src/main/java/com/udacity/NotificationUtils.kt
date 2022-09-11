package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Icon
import android.icu.text.CaseMap
import android.os.Build
import androidx.core.app.NotificationCompat

// Notification ID.
private val NOTIFICATION_ID = 0
// TODO: extension function to send messages (GIVEN)

fun NotificationManager.sendNotification(
    title: String,
    status: Boolean,
    applicationContext: Context
) {

    // TODO: create intent
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    // TODO: create PendingIntent
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // TODO:  add action
    val downloadIntent = Intent(applicationContext, DetailActivity::class.java).apply {
        putExtra(EXTRA_NAME, title)
        putExtra(EXTRA_DOWNLOAD_STATUS, status)
    }

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        downloadIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    //  TODO: create channel
    val notificationChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(
            applicationContext.getString(R.string.notification_channel_id),
            applicationContext.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH
        )
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    notificationChannel.enableLights(true)
    notificationChannel.lightColor = Color.GREEN
    notificationChannel.enableVibration(true)

    val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(notificationChannel)

    //  TODO: Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )

        // TODO: set title, text and icon to builder
        .setSmallIcon(R.drawable.ic_baseline_cloud_download_24)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(applicationContext.getString(R.string.notification_description))
        // TODO: set content intent
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

        // TODO: add status action
        .addAction(
            R.drawable.ic_assistant_black_24dp,
            applicationContext.getString(R.string.check_status),
            contentPendingIntent
        )
        // TODO: set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    // TODO: call notify
    notify(NOTIFICATION_ID, builder.build())

}

// TODO: Cancel all notifications
fun NotificationManager.cancelNotifications() {
    cancelAll()
}