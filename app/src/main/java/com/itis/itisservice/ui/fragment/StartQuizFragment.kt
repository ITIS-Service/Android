package com.itis.itisservice.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.App
import com.itis.itisservice.R
import com.itis.itisservice.model.Question
import com.itis.itisservice.mvp.presenter.StartQuizPresenter
import com.itis.itisservice.mvp.view.StartQuizView
import com.itis.itisservice.tools.QuizManager
import com.itis.itisservice.ui.activity.MainActivity
import com.itis.itisservice.utils.AppPreferencesHelper
import com.itis.itisservice.utils.Constants.QUIZ_IS_AGAIN
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_start_quiz.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import javax.inject.Inject

class StartQuizFragment : BaseFragment(), StartQuizView {

    @InjectPresenter
    lateinit var presenter: StartQuizPresenter

    @Inject
    lateinit var quizManager: QuizManager

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

    companion object {
        fun newInstance(isAgain: Boolean = false): StartQuizFragment {
            val args = Bundle()
            args.putBoolean(QUIZ_IS_AGAIN, isAgain)

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

        val isAgain = arguments?.getBoolean(QUIZ_IS_AGAIN) ?: false

        btn_start_quiz.setOnClickListener { presenter.loadQuestions() }

        if (isAgain) {
            btn_skip.setOnClickListener {
                baseActivity.onBackPressed()
            }
        } else {
            btn_skip.setOnClickListener {
                val intent = Intent(baseActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
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
//        progressHUD?.show()
        showProgressBar()
    }

    override fun hideProgress() {
//        progressHUD?.dismiss()
        hideProgressBar()
    }
}