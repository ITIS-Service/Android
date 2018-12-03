package com.itis.itisservice.mvp.presenter

import android.support.v4.app.FragmentManager
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.model.Answer
import com.itis.itisservice.model.Answers
import com.itis.itisservice.mvp.view.QuizView
import com.itis.itisservice.tools.QuizManager
import com.itis.itisservice.utils.AppPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class QuizPresenter : MvpPresenter<QuizView>() {

    @Inject
    lateinit var quizManager: QuizManager

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
        loadQuestion()
    }

    private fun sendAnswers(answers: Map<String?, Int?>) {
        compositeDisposable.add(
                userApi
                        .sendAnswers(sharedPreferences.getAccessToken(), Answers(answers))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { viewState.showProgress() }
                        .doAfterTerminate { viewState.hideProgress() }
                        .subscribe({
                            if (it.code() == 200) {
                                quizManager.currentNumber = 0
                                viewState.finishQuiz()
                            } else {
                                viewState.onCodeInvalid()
                            }
                        }, { error -> viewState.onConnectionError(error) })
        )
    }

    fun nextQuestion(fragmentManager: FragmentManager?) {
        if (hasNextQuestion()) {
            quizManager.nextQuestion(fragmentManager)
        } else {
            quizManager.answers.forEach { (key, value) ->
                Log.d("map answers$key ", value.toString())
            }
            sendAnswers(quizManager.answers)
        }
    }

    fun writeAnswer(index: Int) {
        quizManager.writeAnswer(index)
    }

    private fun hasNextQuestion(): Boolean {
        return quizManager.hasNextQuestion()
    }

    private fun loadQuestion() {
        val question = quizManager.getQuestion()
        viewState.showQuestion(question)

        //todo move to one method
        viewState.showAnswers(question.answers as? List<Answer>)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}