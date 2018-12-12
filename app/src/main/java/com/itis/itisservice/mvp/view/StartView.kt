package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.MvpView

interface StartView : MvpView {
    fun startSignIn()
    fun signedIn()
}