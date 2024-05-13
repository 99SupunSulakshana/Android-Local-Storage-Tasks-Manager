package com.example.todoapplication.reminder

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todoapplication.R
import com.example.todoapplication.reminder.destination.DestinationActivity
import com.example.todoapplication.ui.screens.mytodolistscreen.MyToDoListsActivity

class TodoReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeIntentLaunch")
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, MyToDoListsActivity::class.java)
        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        val title = "Reminder - Todo App"
        val description = ""
        val channelId = "i.apps.notifications"
        val builder = NotificationCompat.Builder(context!!, channelId)
        builder.setSmallIcon(R.drawable.alarms)
        builder.setContentTitle(title)
        builder.setContentText(description)
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        builder.setAutoCancel(true)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = notificationManager.getNotificationChannel(channelId)
            if (notificationChannel == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                notificationChannel = NotificationChannel(channelId, "todo", importance)
                notificationChannel.lightColor = context.getColor(R.color.white)
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
        notificationManager.notify(0, builder.build())
    }

}