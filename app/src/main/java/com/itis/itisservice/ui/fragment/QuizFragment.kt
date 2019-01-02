package com.itis.itisservice.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.LinearLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.Answer
import com.itis.itisservice.model.Question
import com.itis.itisservice.mvp.presenter.QuizPresenter
import com.itis.itisservice.mvp.view.QuizView
import com.itis.itisservice.utils.Constants.NUM_QUESTION_KEY
import kotlinx.android.synthetic.main.fragment_quiz.*
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.Toast
import com.itis.itisservice.ui.activity.MainActivity
import com.itis.itisservice.utils.toDp
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class QuizFragment : BaseFragment(), QuizView {

    private var numQuestion: Int = 0

    @InjectPresenter
    lateinit var presenter: QuizPresenter

    companion object {
        fun newInstance(numQuestion: Int): QuizFragment {
            val args = Bundle()
            args.putInt(NUM_QUESTION_KEY, numQuestion)

            val fragment = QuizFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.supportActionBar?.hide()

        numQuestion = arguments?.getInt(NUM_QUESTION_KEY) ?: 0
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_quiz

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_quiz
    }

    override fun showQuestion(question: Question) {
        tv_question.text = "${numQuestion + 1}.${question.title}"
    }

    override fun showAnswers(answers: List<Answer>?) {
        //btn_answer1.text = answers?.get(0)?.title
        val size = answers?.size ?: 0
        for (i in 0 until size) {
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(toDp(8, baseActivity), 0, toDp(8, baseActivity), toDp(16, baseActivity))

            val button = Button(baseActivity)

            button.setBackgroundResource(R.drawable.btn_answer_selector)
            button.setTextColor(ContextCompat.getColor(baseActivity, R.color.white))
            button.text = answers?.get(i)?.title

            container_answers.addView(button, params)
            button.setOnClickListener {
                presenter.writeAnswer(i)
                presenter.nextQuestion(baseActivity.myFragmentManager)
            }
        }
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка отправки ответов на сервер", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        showProgressBar()
    }

    override fun hideProgress() {
        hideProgressBar()
    }

    override fun onStop() {
        super.onStop()
        hideProgressBar()
    }

    override fun finishQuiz() {
        // todo fix this back stack
//        baseActivity.clearFragmentsStack()
//        baseActivity.setContent(CourseListFragment.newInstance(), true)
        val intent = Intent(baseActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}