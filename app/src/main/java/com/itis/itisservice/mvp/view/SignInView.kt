package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType


interface SignInView : BaseView {
    fun enableLoginButton()
    fun disableLoginButton()

    @StateStrategyType(SkipStrategy::class)
    fun onLoginSuccess()

    fun forceUpdateEmailPassword()
}