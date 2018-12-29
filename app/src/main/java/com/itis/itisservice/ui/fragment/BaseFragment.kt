package com.itis.itisservice.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arellomobile.mvp.MvpAppCompatFragment
import com.itis.itisservice.R
import com.itis.itisservice.ui.activity.BaseActivity
import com.kaopiz.kprogresshud.KProgressHUD

abstract class BaseFragment : MvpAppCompatFragment() {

    @get:LayoutRes
    protected abstract val mainContentLayout: Int

    val baseActivity: BaseActivity
        get() = activity as BaseActivity

    var progressHUD: KProgressHUD? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(mainContentLayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressHUD = createProgressHud()
    }

    fun createToolbarTitle(context: Context): String {
        return context.getString(onCreateToolbarTitle())
    }

    private fun createProgressHud(): KProgressHUD {
        return KProgressHUD.create(baseActivity)
                .setDimAmount(0.5f)
                .setBackgroundColor(ContextCompat.getColor(baseActivity, R.color.colorTextField))
    }

    @StringRes
    abstract fun onCreateToolbarTitle(): Int
}
