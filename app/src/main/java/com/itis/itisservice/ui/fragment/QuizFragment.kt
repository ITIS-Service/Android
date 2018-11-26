package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.Question
import com.itis.itisservice.mvp.presenter.QuizPresenter
import com.itis.itisservice.mvp.view.QuizView

class QuizFragment : BaseFragment(), QuizView {

    @InjectPresenter
    lateinit var presenter: QuizPresenter

    companion object {
        fun newInstance(): QuizFragment {
            val args = Bundle()

            val fragment = QuizFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity?.supportActionBar?.hide()
    }

    override fun showQuestions(list: List<Question>) {
        list.forEach {
            Log.d("Question title: ", it.title)
        }
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_quiz

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_quiz
    }
}