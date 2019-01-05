package com.itis.itisservice.ui.activity

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.App

import com.itis.itisservice.R
import com.itis.itisservice.db.ProfileRepository
import com.itis.itisservice.mvp.presenter.StartPresenter
import com.itis.itisservice.mvp.view.StartView
import com.itis.itisservice.tools.QuizManager
import com.itis.itisservice.ui.fragment.SignInFragment
import com.itis.itisservice.ui.fragment.StartQuizFragment
import com.itis.itisservice.utils.Constants.QUIZ_IS_AGAIN
import javax.inject.Inject

class StartActivity : BaseActivity(), StartView {

    @InjectPresenter
    lateinit var presenter: StartPresenter

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
        val isAgain = intent.getBooleanExtra(QUIZ_IS_AGAIN, false)

        if (isAgain) {
            setContent(StartQuizFragment.newInstance(isAgain), false)
        } else {
            if (presenter.isPassQuiz()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}
