package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.mvp.view.BaseView
import com.itis.itisservice.utils.NetworkManager
import javax.inject.Inject

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    @Inject
    lateinit var networkManager: NetworkManager

    fun loadData() {
        if (networkManager.hasInternetConnection()) {
            onLoadDataObservable()
        } else {
            viewState.onConnectionError(Throwable())
        }
    }

    abstract fun onLoadDataObservable()
}