package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.db.PointsRepository
import com.itis.itisservice.model.UserPoints
import com.itis.itisservice.mvp.view.ProgressView
import com.itis.itisservice.utils.AppPreferencesHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import javax.inject.Inject

@InjectViewState
class ProgressPresenter : MvpPresenter<ProgressView>() {

    @Inject
    lateinit var pointsRepository: PointsRepository

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

    @Inject
    lateinit var userApi: UserApi

    private val compositeDisposable = CompositeDisposable()

    init {
        App.applicationComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.getCourseId()
    }

    fun loadPoints(id: Int?) {
        compositeDisposable.add(userApi
                .points(sharedPreferences.getAccessToken(), id)
                .flatMap { pointsRepository.addPoints(it, id) }
                .onErrorResumeNext(pointsRepository.getPoints(id))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe({
                    viewState.showPoints(it)
                }, { error -> viewState.onConnectionError(error) })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}