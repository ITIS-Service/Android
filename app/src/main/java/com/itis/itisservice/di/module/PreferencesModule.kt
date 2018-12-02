package com.itis.itisservice.di.module

import android.content.Context
import com.itis.itisservice.tools.QuizManager
import com.itis.itisservice.utils.AppPreferencesHelper
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
}