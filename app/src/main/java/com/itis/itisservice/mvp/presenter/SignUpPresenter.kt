package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.api.UserApi
import com.itis.itisservice.db.ProfileRepository
import com.itis.itisservice.model.Profile
import com.itis.itisservice.model.User
import com.itis.itisservice.mvp.view.SignUpView
import com.itis.itisservice.tools.validation.EmailValidator
import com.itis.itisservice.tools.validation.PasswordValidator
import com.itis.itisservice.utils.NetworkManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class SignUpPresenter : MvpPresenter<SignUpView>() {

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var profileRepository: ProfileRepository

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
        if (networkManager.isOnline()) {
            compositeDisposable.add(userApi
                    .signUp(user)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { viewState.showProgress() }
                    .doAfterTerminate { viewState.hideProgress() }
                    .subscribe(
                            {
                                profileRepository.addProfile(it)
                                viewState.onRegistrationSuccess()
                            }, { error -> viewState.onConnectionError(error) })
            )
        } else {
            viewState.onConnectionError(Throwable())
        }
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