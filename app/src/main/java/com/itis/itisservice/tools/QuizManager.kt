package com.itis.itisservice.tools

import android.support.v4.app.FragmentManager
import android.util.Log
import com.itis.itisservice.R
import com.itis.itisservice.model.Question
import com.itis.itisservice.ui.fragment.QuizFragment
import java.util.ArrayList

class QuizManager {

    var currentNumber: Int = 0
    var questions: MutableList<Question> = ArrayList()
    var answers: MutableMap<String?, Int?> = HashMap()

    fun loadQuestions(list: List<Question>) {
        questions.clear()
        questions.addAll(list)
    }

    fun writeAnswer(index: Int) {
        val currentQuestion = questions[currentNumber]
        answers[currentQuestion.id.toString()] = currentQuestion.answers?.get(index)?.id?.toInt()
        Log.d("answers" + index.toString(), answers[currentQuestion.id.toString()].toString())
    }

    fun getQuestion(): Question {
        return questions[currentNumber]
    }

    fun hasNextQuestion(): Boolean {
        return currentNumber != questions.size - 1
    }

    fun nextQuestion(fragmentManager: FragmentManager?) {
        currentNumber++
        val fragment = QuizFragment.newInstance(currentNumber)
        val transaction = fragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right)
        transaction?.addToBackStack(null)
        transaction?.add(R.id.main_wrapper, fragment)?.commit()
    }
}