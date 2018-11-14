package com.itis.itisservice

import android.app.Application
import android.content.Context
import com.itis.itisservice.di.component.ApplicationComponent
import com.itis.itisservice.di.component.DaggerApplicationComponent
import com.itis.itisservice.di.module.ApplicationModule
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.rx.RealmObservableFactory

class App : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
                .rxFactory(RealmObservableFactory())
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(configuration)

        initComponent()
    }

    private fun initComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this)).build()
    }
}
