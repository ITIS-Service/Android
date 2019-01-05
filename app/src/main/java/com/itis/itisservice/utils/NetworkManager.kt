package com.itis.itisservice.utils

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Observable
import java.net.HttpURLConnection
import java.net.URL

class NetworkManager(var context: Context) {

    private fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun hasInternetConnection(): Observable<Boolean> {
        return Observable.fromCallable {
            try {
                if (!isOnline()) {
                    false
                }

                val url = URL("https://itis-courses.herokuapp.com")
                val urlc = url.openConnection() as HttpURLConnection
                urlc.connectTimeout = 2000
                urlc.connect()

                true

            } catch (e: Exception) {
                false
            }
        }
    }
}