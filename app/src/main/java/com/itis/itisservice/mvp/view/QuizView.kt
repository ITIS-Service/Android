package com.itis.itisservice.mvp.view

import com.itis.itisservice.model.Answer
import com.itis.itisservice.model.Question

interface QuizView : BaseView {
    fun showQuestion(question: Question)
    fun showAnswers(answers: List<Answer>?)
    fun finishQuiz()
}