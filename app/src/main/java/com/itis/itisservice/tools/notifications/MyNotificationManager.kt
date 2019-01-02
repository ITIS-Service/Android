package com.itis.itisservice.tools.notifications

import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.app.PendingIntent
import com.itis.itisservice.ui.activity.MainActivity
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.itis.itisservice.R
import com.itis.itisservice.utils.Constants.CHANNEL_ID
import android.media.RingtoneManager

class MyNotificationManager(var context: Context) {

    fun displayNotification(title: String?, body: String?) {

        val pattern = longArrayOf(0, 100, 200, 300)
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setSound(sound)

        val resultIntent = Intent(context, MainActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        builder.setContentIntent(pendingIntent)

        val mNotifyMgr = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        mNotifyMgr.notify(1, builder.build())
    }
}