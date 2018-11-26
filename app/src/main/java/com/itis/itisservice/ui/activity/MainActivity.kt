package com.itis.itisservice.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter

import com.itis.itisservice.R
import com.itis.itisservice.mvp.presenter.MainPresenter
import com.itis.itisservice.mvp.view.MainView
import com.itis.itisservice.ui.fragment.SignInFragment
import com.itis.itisservice.ui.fragment.StartQuizFragment

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override val mainContentLayout: Int
        get() = R.layout.activity_main

    override fun startSignIn() {
        setContent(SignInFragment.newInstance(), false)
    }

    override fun signedIn() {
        setContent(StartQuizFragment.newInstance(), false)
    }
}
