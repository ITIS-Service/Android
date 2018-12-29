package com.itis.itisservice.tools.notifications

import android.app.Notification
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
                .setVibrate(pattern)
                .setSound(sound)

        /*
        *  Clicking on the notification will take us to this intent
        *  Right now we are using the MainActivity as this is the only activity we have in our application
        *  But for your project you can customize it as you want
        * */

        val resultIntent = Intent(context, MainActivity::class.java)

        /*
        *  Now we will create a pending intent
        *  The method getActivity is taking 4 parameters
        *  All paramters are describing themselves
        *  0 is the request code (the second parameter)
        *  We can detect this code in the activity that will open by this we can get
        *  Which notification opened the activity
        * */
        val pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        /*
        *  Setting the pending intent to notification builder
        * */

        builder.setContentIntent(pendingIntent)

        val mNotifyMgr = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        /*
        * The first parameter is the notification id
        * better don't give a literal here (right now we are giving a int literal)
        * because using this id we can modify it later
        * */
        mNotifyMgr.notify(1, builder.build())
    }
}