package com.itis.itisservice.di.module

import android.content.Context
import com.itis.itisservice.tools.notifications.MyNotificationManager
import com.itis.itisservice.tools.QuizManager
import com.itis.itisservice.utils.AppPreferencesHelper
import com.itis.itisservice.utils.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Singleton
    @Provides
    fun provideLocalStorage(context: Context): AppPreferencesHelper {
        return AppPreferencesHelper(context)
    }

    @Singleton
    @Provides
    fun provideQuizManager(): QuizManager {
        return QuizManager()
    }

    @Singleton
    @Provides
    fun provideNotificationManager(context: Context): MyNotificationManager {
        return MyNotificationManager(context)
    }

    @Singleton
    @Provides
    fun provideNetworkManager(context: Context): NetworkManager {
        return NetworkManager(context)
    }
}