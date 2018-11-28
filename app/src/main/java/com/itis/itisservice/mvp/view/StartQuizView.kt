package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.itisservice.model.Question

interface StartQuizView: BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun openQuiz(questions: List<Question>)
}