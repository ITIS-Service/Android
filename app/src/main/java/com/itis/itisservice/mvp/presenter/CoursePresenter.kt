package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.mvp.view.CourseView
import com.itis.itisservice.utils.AppPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class CoursePresenter : MvpPresenter<CourseView>() {

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

    @Inject
    lateinit var userApi: UserApi

    private val compositeDisposable = CompositeDisposable()

    init {
        App.applicationComponent.inject(this)
    }

    fun signUp(id: Int? ) {
        compositeDisposable.add(userApi
                .signUpCourse(sharedPreferences.getAccessToken(), id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe({
                    viewState.showDetails(it)
                }, { error -> viewState.onConnectionError(error) })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}