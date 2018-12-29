package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.model.RegisterDevice
import com.itis.itisservice.mvp.view.SettingsView
import com.itis.itisservice.utils.AppPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class SettingsPresenter : MvpPresenter<SettingsView>() {

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

    @Inject
    lateinit var userApi: UserApi

    private val compositeDisposable = CompositeDisposable()

    init {
        App.applicationComponent.inject(this)
    }

    fun unregisterDevice() {
        compositeDisposable.add(
                userApi
                        .unregisterDevice(sharedPreferences.getAccessToken(), RegisterDevice(token = sharedPreferences.getDeviceToken()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { viewState.showProgress() }
                        .doAfterTerminate { viewState.hideProgress() }
                        .subscribe({
                            sharedPreferences.deleteToken()
                            viewState.unregisterSuccess()
                        }, { error -> viewState.onConnectionError(error) })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}