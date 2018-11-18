package com.itis.itisservice.mvp.view


interface SignUpView : BaseView {
    fun onValidEmail(error: Boolean)
    fun onValidPassword(error: Boolean)
    fun onValidConfirmPassword(error: Boolean)
    fun onRegistrationSuccess()
}