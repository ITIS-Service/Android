package com.itis.itisservice.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.db.ProfileRepository
import com.itis.itisservice.model.RegisterDevice
import com.itis.itisservice.model.User
import com.itis.itisservice.mvp.view.SignInView
import com.itis.itisservice.tools.validation.EmailValidator
import com.itis.itisservice.tools.validation.PasswordValidator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import com.itis.itisservice.utils.AppPreferencesHelper
import com.itis.itisservice.utils.Two
import io.reactivex.Single

@InjectViewState
class SignInPresenter : MvpPresenter<SignInView>() {

    @Inject
    lateinit var profileRepository: ProfileRepository

    @Inject
    lateinit var emailValidator: EmailValidator

    @Inject
    lateinit var passwordValidator: PasswordValidator

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

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
        compositeDisposable.addAll(
                Single.zip(
                        userApi.signIn(User(email = email, password = password)),
                        userApi.signIn2(User(email = email, password = password)),
                        Two.zipFunc()
                )
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { viewState.showProgress() }
                        .doAfterTerminate { viewState.hideProgress() }
                        .subscribe({
                            if (it.first?.code() == 200) {
                                val userToken = it.first?.headers()?.get("Authorization")
                                Log.d("SIGN_IN PRESENTER", "token: $userToken")
                                //createSharedPreferences(usertoken)
                                Log.d("SIGN_IN PRESENTER", "${it.second?.email}")
                                it.second?.let { profile -> profileRepository.addProfile(profile) }
                                registerDevice(userToken)
                                //viewState.onLoginSuccess()
                            } else {
                                viewState.onCodeInvalid()
                            }
                        }, { error -> viewState.onConnectionError(error) })
        )
    }

    private fun registerDevice(userToken: String?) {
        compositeDisposable.add(
                userApi
                        .registerDevice(userToken, RegisterDevice("Xiaomi Redmi Note 3 Pro", "Android 6.0.1",
                                sharedPreferences.getDeviceToken(), "Android"))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { viewState.showProgress() }
                        .doAfterTerminate { viewState.hideProgress() }
                        .subscribe({
                            createSharedPreferences(userToken)
                            viewState.onLoginSuccess()
                        }, { error -> viewState.onConnectionError(error) })
        )
    }

    private fun createSharedPreferences(token: String?) {
        sharedPreferences.setAccessToken(token)
    }

    fun onLoginClicked(email: String, password: String, token: String?) {
        if (emailValidator.isValid(email) && passwordValidator.isValid(password)) {
            setDeviceToken(token)
            startLogin(email, password)
        } else {
            viewState.forceUpdateEmailPassword()
        }
    }

    private fun setDeviceToken(token: String?) {
        sharedPreferences.setDeviceToken(token)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}