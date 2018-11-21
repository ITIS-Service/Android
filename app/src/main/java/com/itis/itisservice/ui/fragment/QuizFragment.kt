package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.View
import com.itis.itisservice.R

class QuizFragment : BaseFragment() {

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

    override val mainContentLayout: Int
        get() = R.layout.fragment_quiz

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_quiz
    }
}