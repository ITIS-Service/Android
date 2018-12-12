package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.MvpView
import com.itis.itisservice.ui.fragment.BaseFragment

interface MainView : MvpView {
    fun setFragment(fragment: BaseFragment, addToBackStack: Boolean)
}