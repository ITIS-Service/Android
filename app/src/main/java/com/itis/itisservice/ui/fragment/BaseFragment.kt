package com.itis.itisservice.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arellomobile.mvp.MvpAppCompatFragment
import com.itis.itisservice.ui.activity.BaseActivity

abstract class BaseFragment : MvpAppCompatFragment() {

    @get:LayoutRes
    protected abstract val mainContentLayout: Int

    val baseActivity: BaseActivity
        get() = activity as BaseActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(mainContentLayout, container, false)
    }

    fun createToolbarTitle(context: Context): String {
        return context.getString(onCreateToolbarTitle())
    }

    @StringRes
    abstract fun onCreateToolbarTitle(): Int
}
