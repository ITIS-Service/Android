package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.itisservice.model.Question
import io.reactivex.annotations.NonNull

interface QuizView : MvpView {
    fun showQuestions(@NonNull list: List<Question>)

    @StateStrategyType(SkipStrategy::class)
    fun onCodeInvalid()

    @StateStrategyType(SkipStrategy::class)
    fun onConnectionError(error: Throwable)
}