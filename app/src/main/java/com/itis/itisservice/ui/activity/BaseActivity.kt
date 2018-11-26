package com.itis.itisservice.ui.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.TextView

import com.arellomobile.mvp.MvpAppCompatActivity
import com.crashlytics.android.Crashlytics
import com.itis.itisservice.R
import com.itis.itisservice.ui.fragment.BaseFragment
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.toolbar_layout.*

abstract class BaseActivity : MvpAppCompatActivity() {

    var myFragmentManager: FragmentManager? = null

    @get:LayoutRes
    protected abstract val mainContentLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base)

        Fabric.with(this, Crashlytics())

        setSupportActionBar(toolbar)

        myFragmentManager = supportFragmentManager

        layoutInflater.inflate(mainContentLayout, main_wrapper)
    }

    override fun onBackPressed() {
        if (myFragmentManager?.backStackEntryCount!! > 0) {
            myFragmentManager?.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    fun fragmentOnScreen(fragment: BaseFragment) {
        setToolbarTitle(fragment.createToolbarTitle(this))
    }

    private fun setToolbarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            toolbar_title.text = title
        }
    }

    fun setBackArrow(enabled: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
            supportActionBar?.setDisplayShowHomeEnabled(enabled)
            toolbar?.setNavigationOnClickListener { onBackPressed() }
        }
    }

    fun setContent(fragment: BaseFragment, addToBackStack: Boolean) {
        val fragmentTransaction = myFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_wrapper, fragment)
        if (addToBackStack) {
            fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            fragmentTransaction?.addToBackStack(null)
        }
        fragmentTransaction?.commit()
    }
}
