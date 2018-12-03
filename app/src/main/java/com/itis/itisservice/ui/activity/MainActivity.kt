package com.itis.itisservice.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.App

import com.itis.itisservice.R
import com.itis.itisservice.mvp.presenter.MainPresenter
import com.itis.itisservice.mvp.view.MainView
import com.itis.itisservice.tools.QuizManager
import com.itis.itisservice.ui.fragment.SignInFragment
import com.itis.itisservice.ui.fragment.StartQuizFragment
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var quizManager: QuizManager

    override val mainContentLayout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.applicationComponent.inject(this)
    }

    override fun startSignIn() {
        setContent(SignInFragment.newInstance(), false)
    }

    override fun signedIn() {
        setContent(StartQuizFragment.newInstance(), false)
    }

    /*override fun onBackPressed() {
        if (myFragmentManager?.backStackEntryCount!! > 0) {
            if (quizManager.currentNumber != 0) quizManager.currentNumber--
            myFragmentManager?.popBackStack()
        } else {
            super.onBackPressed()
        }
    }*/

    override fun onBackPressed() {
        if (quizManager.currentNumber != 0) quizManager.currentNumber--
        super.onBackPressed()
    }
}
