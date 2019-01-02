package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.model.UserSettings
import com.itis.itisservice.mvp.view.NotificationView
import com.itis.itisservice.utils.AppPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class NotificationPresenter : MvpPresenter<NotificationView>() {

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

    @Inject
    lateinit var userApi: UserApi

    private var compositeDisposable = CompositeDisposable()

    init {
        App.applicationComponent.inject(this)
    }

    fun changeNotifications(userSettings: UserSettings) {
        compositeDisposable.add(userApi
                .changeNotifications(sharedPreferences.getAccessToken(), userSettings)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, { error -> viewState.onConnectionError(error) })
        )
    }

    fun setStatusNotification(isChecked: Boolean) {
        sharedPreferences.setStatusNotification(isChecked)
    }

    fun setPointsNotification(isChecked: Boolean) {
        sharedPreferences.setPointsNotification(isChecked)
    }

    fun getStatusNotification(): Boolean {
        return sharedPreferences.getStatusNotification()
    }

    fun getPointsNotification(): Boolean {
        return sharedPreferences.getPointsNotification()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}