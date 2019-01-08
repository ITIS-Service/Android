package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface SignInView : BaseView {
    fun enableLoginButton()
    fun disableLoginButton()
    fun onLoginSuccess(passedQuiz: Boolean?)
    fun forceUpdateEmailPassword()
}