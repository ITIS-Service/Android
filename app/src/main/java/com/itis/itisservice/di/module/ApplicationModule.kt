package com.itis.itisservice.di.module

import android.app.Application
import android.content.Context
import com.itis.itisservice.utils.AppPreferencesHelper

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val mApplication: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return mApplication.applicationContext
    }
}