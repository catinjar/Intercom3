package com.example.obir.intercom3.Service

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "JSA-FCM"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage!!.notification != null) {
            val prefs = getSharedPreferences("com.example.obir.intercom3.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE) ?: return

            with (prefs.edit()) {
                putString("title", remoteMessage.notification?.title!!)
                putString("body", remoteMessage.notification?.body!!)
                putString("url", remoteMessage.data["url"]!!)
                //putString("stream", remoteMessage.data["stream"]!!)
                //putString("cmd", remoteMessage.data["cmd"]!!)
                apply()
            }

            Log.e(TAG, "Title: " + remoteMessage.notification?.title!!)
            Log.e(TAG, "Body: " + remoteMessage.notification?.body!!)
        }

        if (remoteMessage.data.isNotEmpty()) {
            Log.e(TAG, "Data: " + remoteMessage.data)
        }
    }

}