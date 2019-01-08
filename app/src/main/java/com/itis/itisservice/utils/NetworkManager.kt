package com.itis.itisservice.utils

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Observable
import java.net.HttpURLConnection
import java.net.URL

class NetworkManager(var context: Context) {

    fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun hasInternetConnection(): Boolean {
        try {
            if (!isOnline()) {
                return false
            }

            val url = URL("https://itis-courses.herokuapp.com")
            val urlc = url.openConnection() as HttpURLConnection
            urlc.connectTimeout = 2000
            urlc.connect()

            return true

        } catch (e: Exception) {
            return false
        }
    }
}