package com.itis.itisservice.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.model.ChangePasswordForm
import com.itis.itisservice.mvp.view.AccountView
import com.itis.itisservice.tools.validation.PasswordValidator
import com.itis.itisservice.utils.AppPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class AccountPresenter : MvpPresenter<AccountView>() {

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

    @Inject
    lateinit var passwordValidator: PasswordValidator

    @Inject
    lateinit var userApi: UserApi

    private var compositeDisposable = CompositeDisposable()

    init {
        App.applicationComponent.inject(this)
    }

    fun changePassword(newPassword: String?, oldPassword: String?) {
        compositeDisposable.add(userApi
                .changePassword(sharedPreferences.getAccessToken(), ChangePasswordForm(newPassword, oldPassword))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe({
                    if (it.success == true) {
                        viewState.onChangePasswordSuccess()
                    } else {
                        viewState.onCodeInvalid()
                    }
                }, { error -> viewState.onConnectionError(error) })
        )
    }

    fun isPasswordValid(password: String) {
        if (!passwordValidator.isValid(password)) {
            viewState.onValidNewPassword(true)
        } else {
            viewState.onValidNewPassword(false)
        }
    }

    fun isConfirmPasswordValid(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            viewState.onValidConfirmPassword(true)
        } else {
            viewState.onValidConfirmPassword(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}