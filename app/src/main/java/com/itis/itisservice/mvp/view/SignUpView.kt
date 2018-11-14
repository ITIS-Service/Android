package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.MvpView

interface SignUpView : MvpView {
    fun showValidationMessage()
    fun showProgress()
    fun hideProgress()
}