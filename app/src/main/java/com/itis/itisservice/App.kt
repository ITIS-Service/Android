package com.itis.itisservice

import android.app.Application
import android.content.Context
import android.os.Build
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.itis.itisservice.di.component.ApplicationComponent
import com.itis.itisservice.di.component.DaggerApplicationComponent
import com.itis.itisservice.di.module.ApplicationModule
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.rx.RealmObservableFactory

class App : MultiDexApplication() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initComponent()
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
                .rxFactory(RealmObservableFactory())
                .schemaVersion(BuildConfig.VERSION_CODE.toLong())
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(configuration)
    }

    private fun initComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this)).build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
