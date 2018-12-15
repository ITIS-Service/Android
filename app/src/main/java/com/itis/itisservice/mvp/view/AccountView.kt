package com.itis.itisservice.mvp.view

interface AccountView : BaseView {
    fun onValidNewPassword(error: Boolean)
    fun onValidConfirmPassword(error: Boolean)
    fun onChangePasswordSuccess()
}