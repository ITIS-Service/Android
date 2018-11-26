package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.MvpView

interface MainView : MvpView {
    fun startSignIn()
    fun signedIn()
}