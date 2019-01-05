package com.itis.itisservice.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.transition.Fade
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.mvp.presenter.MainPresenter
import com.itis.itisservice.mvp.view.MainView
import com.itis.itisservice.ui.fragment.BaseFragment
import com.itis.itisservice.ui.fragment.CourseListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_internet_error.*

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottom_bar.setOnNavigationItemSelectedListener { item ->
            clearFragmentsStack()
            presenter.bottomBarItemClick(item.itemId)
            true
        }

        setFragment(CourseListFragment.newInstance(), false)
    }

    override fun setFragment(fragment: BaseFragment, addToBackStack: Boolean) {
        setContent(fragment, addToBackStack)
    }

    override val mainContentLayout: Int
        get() = R.layout.activity_main
}