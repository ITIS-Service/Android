package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.View
import com.itis.itisservice.R
import kotlinx.android.synthetic.main.fragment_start_quiz.*

class StartQuizFragment : BaseFragment() {

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
        baseActivity?.supportActionBar?.hide()
        btn_start_quiz.setOnClickListener { baseActivity?.setContent(QuizFragment.newInstance(), true) }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_start_quiz

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_quiz
    }

}