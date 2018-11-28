package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.Question
import com.itis.itisservice.mvp.presenter.QuizPresenter
import com.itis.itisservice.mvp.view.QuizView
import com.itis.itisservice.utils.Constants.NUM_QUESTION_KEY
import kotlinx.android.synthetic.main.fragment_quiz.*
import java.util.ArrayList

class QuizFragment : BaseFragment(), QuizView {

    private var questions: MutableList<Question> = ArrayList()
    private var numQuestion: Int = 0

    private lateinit var questionsArray: Array<String>

    @InjectPresenter
    lateinit var presenter: QuizPresenter

    companion object {
        var count: Int = 0

        fun newInstance(): QuizFragment {
            val args = Bundle()
            //args.putInt(NUM_QUESTION_KEY, numQuestion)

            val fragment = QuizFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity?.supportActionBar?.hide()
        //numQuestion = arguments?.getInt(NUM_QUESTION_KEY) ?: 0

        questionsArray = resources.getStringArray(R.array.questions)

        //tv_question.text = questionsArray.get(numQuestion)
        tv_question.text = questionsArray[count]
        btn_answer1.setOnClickListener { nextQuestion() }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_quiz

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_quiz
    }

    private fun nextQuestion() {
        if (count == questionsArray.size - 1) {
            count = 0
            baseActivity?.setContent(StartQuizFragment.newInstance(), false)
        } else {
            count++
            val fragment = QuizFragment.newInstance()
            val fragmentManager = baseActivity?.supportFragmentManager
            val transaction = fragmentManager?.beginTransaction()
            transaction?.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right)
            transaction?.addToBackStack(null)
            transaction?.add(R.id.main_wrapper, fragment)?.commit()
        }
    }
}