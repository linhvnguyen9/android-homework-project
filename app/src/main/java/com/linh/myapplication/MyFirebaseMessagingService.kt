package com.linh.myapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.linh.myapplication.presentation.CalendarUtl
import com.linh.myapplication.presentation.MainActivity
import timber.log.Timber
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Timber.d("Message received")

        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("payload ${remoteMessage.data}")

            val mNotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(NOTIFICATION_CHANNEL, "Thông báo lịch", NotificationManager.IMPORTANCE_HIGH)
                mNotificationManager.createNotificationChannel(channel)
            }

            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)
            }

            val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Bạn có lịch " + remoteMessage.data["name"] + " của thầy/cô ${remoteMessage.data["teacherName"]}")
                .setContentText("Lúc ${CalendarUtl.toFormattedTime(remoteMessage.data["timestamp"]?.toLong() ?: Calendar.getInstance().timeInMillis)}")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }
        }

        remoteMessage.notification?.let {
            Timber.d("Notification body")
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL = "NOTIFICATION_CHANNEL"
    }
}