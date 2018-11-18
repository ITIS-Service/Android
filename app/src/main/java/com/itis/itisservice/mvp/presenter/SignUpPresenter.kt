package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.model.User
import com.itis.itisservice.mvp.view.SignUpView
import com.itis.itisservice.tools.validation.EmailValidator
import com.itis.itisservice.tools.validation.PasswordValidator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class SignUpPresenter : MvpPresenter<SignUpView>() {

    @Inject
    lateinit var emailValidator: EmailValidator

    @Inject
    lateinit var passwordValidator: PasswordValidator

    @Inject
    lateinit var userApi: UserApi

    private var compositeDisposable = CompositeDisposable()

    init {
        App.applicationComponent.inject(this)
    }

    fun startRegister(user: User) {
        compositeDisposable.add(userApi
                .signUp(user)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe(
                        {
                            run {
                                if (it.code() == 200) {
                                    viewState.onRegistrationSuccess()
                                } else {
                                    viewState.onCodeInvalid()
                                }
                            }
                        }, { error -> viewState.onConnectionError(error) })
        )
    }

    fun isEmailValid(email: String) {
        if (!emailValidator.isValid(email)) {
            viewState.onValidEmail(true)
        } else {
            viewState.onValidEmail(false)
        }
    }

    fun isPasswordValid(password: String) {
        if (!passwordValidator.isValid(password)) {
            viewState.onValidPassword(true)
        } else {
            viewState.onValidPassword(false)
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