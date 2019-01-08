package com.itis.itisservice.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import com.arellomobile.mvp.MvpAppCompatFragment
import com.itis.itisservice.R
import com.itis.itisservice.ui.activity.BaseActivity
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.fragment_internet_error.*
import kotlinx.android.synthetic.main.toolbar_layout.*

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

    fun showProgressBar() {
        hideKeyboard()
        baseActivity.progressBar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        baseActivity.progressBar?.visibility = View.INVISIBLE
    }

    fun showProgressError(action: () -> Unit) {
        //baseActivity.setContent(InternetErrorFragment.newInstance(), true)
        fragment_internet_error.visibility = View.VISIBLE

        btn_update?.setOnClickListener {
            hideProgressError()
            action()
        }
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    private fun hideProgressError() {
        fragment_internet_error.visibility = View.GONE
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    @StringRes
    abstract fun onCreateToolbarTitle(): Int
}
