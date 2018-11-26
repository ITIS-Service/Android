package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.View
import com.itis.itisservice.App
import com.itis.itisservice.R
import com.itis.itisservice.utils.AppPreferencesHelper
import kotlinx.android.synthetic.main.fragment_start_quiz.*
import javax.inject.Inject

class StartQuizFragment : BaseFragment() {

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
        baseActivity?.supportActionBar?.hide()
        btn_start_quiz.setOnClickListener { baseActivity?.setContent(QuizFragment.newInstance(), true) }
        btn_skip.setOnClickListener { sharedPreferences.deleteToken() }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_start_quiz

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_quiz
    }

}