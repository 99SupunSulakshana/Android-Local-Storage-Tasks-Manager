package com.example.todoapplication.reminder

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.todoapplication.R

const val notificationID = 121
const val titleExtra = "Todo Application"
const val description = "Check your todos. You have to complete a task right now."
const val channelId = "i.apps.notifications"
class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val builder = NotificationCompat.Builder(context, channelId)
        builder.setSmallIcon(R.drawable.empty_list_icon)
        builder.setContentTitle(titleExtra)
        builder.setContentText(description)
        builder.setAutoCancel(true)
        builder.priority = NotificationCompat.PRIORITY_HIGH

        val notificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = notificationManager.getNotificationChannel(channelId)
            if (notificationChannel == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                notificationChannel = NotificationChannel(channelId, "Reference", importance)
                notificationChannel.lightColor = context.getColor(R.color.white)
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
        notificationManager.notify(0, builder.build())
    }
}