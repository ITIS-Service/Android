package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.mvp.view.QuizView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class QuizPresenter : MvpPresenter<QuizView>() {

    @Inject
    lateinit var userApi: UserApi

    private val compositeDisposable = CompositeDisposable()

    init {
        App.applicationComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadQuestions()
    }

    private fun loadQuestions() {
        compositeDisposable.add(userApi
                .questions()
                .subscribe({
                    viewState.showQuestions(it)
                }, { error -> viewState.onConnectionError(error) })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}