package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.model.User
import com.itis.itisservice.mvp.view.SignInView
import com.itis.itisservice.tools.validation.EmailValidator
import com.itis.itisservice.tools.validation.PasswordValidator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.w3c.dom.Text
import javax.inject.Inject

@InjectViewState
class SignInPresenter : MvpPresenter<SignInView>() {

    @Inject
    lateinit var emailValidator: EmailValidator

    @Inject
    lateinit var passwordValidator: PasswordValidator

    @Inject
    lateinit var userApi: UserApi

    private val compositeDisposable = CompositeDisposable()

    private var emailContainsText = false
    private var passwordContainsText = false

    init {
        App.applicationComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        updateLoginButtonState()
    }

    fun onEmailTextChanged(text: CharSequence) {
        emailContainsText = !text.isEmpty()
        updateLoginButtonState()
    }

    fun onPasswordTextChanged(text: CharSequence) {
        passwordContainsText = !text.isEmpty()
        updateLoginButtonState()
    }

    private fun updateLoginButtonState() {
        if (emailContainsText && passwordContainsText) {
            viewState.enableLoginButton()
        } else {
            viewState.disableLoginButton()
        }
    }

    private fun startLogin(email: String, password: String) {
        compositeDisposable.add(
                userApi
                        .signIn(User(email = email, password = password))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { viewState.showProgress() }
                        .doAfterTerminate { viewState.hideProgress() }
                        .subscribe({
                            if (it.code() == 200) {
                                viewState.onLoginSuccess()
                            } else {
                                viewState.onCodeInvalid()
                            }
                        }, { error -> viewState.onConnectionError(error) })
        )
    }

    fun onLoginClicked(email: String, password: String) {
        if (emailValidator.isValid(email) && passwordValidator.isValid(password)) {
            startLogin(email, password)
        } else {
            viewState.forceUpdateEmailPassword()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}