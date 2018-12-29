package com.itis.itisservice.tools.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.itis.itisservice.App
import javax.inject.Inject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationManager: MyNotificationManager

    init {
        App.applicationComponent.inject(this)
    }

    override fun onNewToken(token: String?) {
        Log.d("NEW_TOKEN", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage?.notification?.title
        val body = remoteMessage?.notification?.body

        Log.d("title notification:", title)
        Log.d("body notification:", body)

        notificationManager.displayNotification(title, body)
    }
}