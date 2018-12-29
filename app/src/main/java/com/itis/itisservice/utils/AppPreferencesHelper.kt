package com.itis.itisservice.utils

import android.content.Context
import android.content.SharedPreferences
import com.itis.itisservice.utils.Constants.PREF_KEY_ACCESS_TOKEN
import com.itis.itisservice.utils.Constants.PREF_KEY_DEVICE_TOKEN
import com.itis.itisservice.utils.Constants.SHARED_PREF_NAME

class AppPreferencesHelper(context: Context) {

    private val mPrefs: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun getAccessToken(): String? {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null)
    }

    fun setAccessToken(token: String?) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, token).apply()
    }

    fun deleteToken() {
        mPrefs.edit().clear().apply()
    }

    fun getDeviceToken(): String? {
        return mPrefs.getString(PREF_KEY_DEVICE_TOKEN, null)
    }

    fun setDeviceToken(token: String?) {
        mPrefs.edit().putString(PREF_KEY_DEVICE_TOKEN, token).apply()
    }

    fun getmPrefs(): SharedPreferences {
        return mPrefs
    }
}
