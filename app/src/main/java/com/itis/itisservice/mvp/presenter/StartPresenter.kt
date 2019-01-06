package com.itis.itisservice.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.App
import com.itis.itisservice.db.ProfileRepository
import com.itis.itisservice.mvp.view.StartView
import com.itis.itisservice.utils.AppPreferencesHelper
import javax.inject.Inject

@InjectViewState
class StartPresenter : MvpPresenter<StartView>() {

    @Inject
    lateinit var profileRepository: ProfileRepository

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

    init {
        App.applicationComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkAuth()
    }

    private fun checkAuth() {
        if (sharedPreferences.getAccessToken() == null) {
            viewState.startSignIn()
        } else {
            Log.d("MAIN PRESENTER", "token: ${sharedPreferences.getAccessToken()}")
            viewState.signedIn()
        }
    }

    fun isPassQuiz(): Boolean {
        return profileRepository.getProfile()?.passedQuiz ?: true
    }
}