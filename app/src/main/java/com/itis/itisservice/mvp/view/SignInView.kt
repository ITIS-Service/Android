package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.MvpView

interface SignInView : MvpView {
    fun enableLoginButton()
    fun disableLoginButton()
    fun openProfile()
}