package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun onCodeInvalid()

    fun onConnectionError(error: Throwable)

    fun showProgress()
    fun hideProgress()
}