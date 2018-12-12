package com.itis.itisservice.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis.itisservice.R
import com.itis.itisservice.mvp.view.MainView
import com.itis.itisservice.ui.fragment.CourseListFragment
import com.itis.itisservice.ui.fragment.SettingsFragment

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    fun bottomBarItemClick(id: Int) {
        when (id) {
            R.id.action_item_courses -> viewState.setFragment(CourseListFragment.newInstance(), false)
            R.id.action_item_settings -> viewState.setFragment(SettingsFragment.newInstance(), false)
        }
    }
}