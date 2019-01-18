package com.example.obir.intercom3.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.example.obir.intercom3.Intercom
import com.example.obir.intercom3.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "JSA-FCM"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage!!.data.isNotEmpty()) {
            Log.e(TAG, "Title: " + remoteMessage.data["title"])
            Log.e(TAG, "Body: " + remoteMessage.data["body"])

            val prefs = getSharedPreferences("com.example.obir.intercom3.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE) ?: return

            with (prefs.edit()) {
                putString("video_stream", remoteMessage.data["video_stream"]!!)
                putString("audio_stream", remoteMessage.data["audio_stream"]!!)
                apply()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Intercom3"
                val descriptionText = "Intercom3"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel("INTERCOM3_CHANNEL", name, importance).apply {
                    description = descriptionText
                }
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            val intent = Intent(this, Intercom::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            var mBuilder = NotificationCompat.Builder(this, "INTERCOM3_CHANNEL")
                .setSmallIcon(R.drawable.notification_icon_background)
                .setContentTitle(remoteMessage.data["title"])
                .setContentText(remoteMessage.data["body"])
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(this)) {
                notify("intercom3_call".hashCode(), mBuilder.build())
            }
        }
    }
}