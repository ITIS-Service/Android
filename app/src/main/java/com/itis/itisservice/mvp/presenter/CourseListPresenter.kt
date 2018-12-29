package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.model.course.Course
import com.itis.itisservice.model.course.CourseDetails
import com.itis.itisservice.mvp.view.CourseListView
import com.itis.itisservice.utils.AppPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class CourseListPresenter : MvpPresenter<CourseListView>() {

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
        loadCourses()
    }

    fun loadCourses() {
        compositeDisposable.add(userApi
                .courses(sharedPreferences.getAccessToken())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe({
                    viewState.showCourses(it)
                }, { error -> viewState.onConnectionError(error) })
        )
    }

    private fun loadCourse(id: Int?) {
        compositeDisposable.add(userApi
                .course(sharedPreferences.getAccessToken(), id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe({
                    viewState.showDetails(it)
                }, { error -> viewState.onConnectionError(error) })
        )
    }

    fun onItemClick(item: Course) {
        loadCourse(item.id)
//        viewState.showDetails(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}