package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.App
import com.itis.itisservice.R
import com.itis.itisservice.model.Question
import com.itis.itisservice.mvp.presenter.StartQuizPresenter
import com.itis.itisservice.mvp.view.StartQuizView
import com.itis.itisservice.tools.QuizManager
import com.itis.itisservice.ui.activity.MainActivity
import com.itis.itisservice.utils.AppPreferencesHelper
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_start_quiz.*
import javax.inject.Inject

class StartQuizFragment : BaseFragment(), StartQuizView {

    @InjectPresenter
    lateinit var presenter: StartQuizPresenter

    @Inject
    lateinit var quizManager: QuizManager

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

    companion object {
        fun newInstance(): StartQuizFragment {
            val args = Bundle()

            val fragment = StartQuizFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.applicationComponent.inject(this)
        baseActivity.setBackArrow(false)
        baseActivity.supportActionBar?.hide()

        btn_start_quiz.setOnClickListener { presenter.loadQuestions() }
        btn_skip.setOnClickListener { sharedPreferences.deleteToken() }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_start_quiz

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_quiz
    }

    override fun openQuiz(questions: List<Question>) {
        quizManager.loadQuestions(questions)
        baseActivity.setContent(QuizFragment.newInstance(0), true)
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        (activity as? MainActivity)?.progressBar2?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.progressBar2?.visibility = View.GONE
    }
}