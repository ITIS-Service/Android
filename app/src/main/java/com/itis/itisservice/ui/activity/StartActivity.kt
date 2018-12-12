package com.itis.itisservice.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.App

import com.itis.itisservice.R
import com.itis.itisservice.mvp.presenter.StartPresenter
import com.itis.itisservice.mvp.view.StartView
import com.itis.itisservice.tools.QuizManager
import com.itis.itisservice.ui.fragment.SignInFragment
import com.itis.itisservice.ui.fragment.StartQuizFragment
import javax.inject.Inject

class StartActivity : BaseActivity(), StartView {

    @InjectPresenter
    lateinit var presenter: StartPresenter

    @Inject
    lateinit var quizManager: QuizManager

    override val mainContentLayout: Int
        get() = R.layout.activity_start

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
