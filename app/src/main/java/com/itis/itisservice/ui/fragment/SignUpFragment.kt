package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.mvp.presenter.SignUpPresenter
import com.itis.itisservice.mvp.view.SignUpView
import com.itis.itisservice.tools.validation.validateInput
import com.itis.itisservice.utils.hide
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : BaseFragment(), SignUpView {

    private var disposable = CompositeDisposable()

    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    companion object {
        fun newInstance(): SignUpFragment {
            val args = Bundle()

            val fragment = SignUpFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        validateFields()
    }

    override fun onStop() {
        super.onStop()
        if (!disposable.isDisposed) disposable.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        baseActivity?.setBackArrow(true)
        baseActivity?.fragmentOnScreen(this)
        btn_to_sign_up.setOnClickListener { onRegisterClicked() }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_sign_up

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_sign_up
    }

    override fun showValidationMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        hide(linear_layout_container_sign_up)
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
        onBackPressed()
    }

    private fun validateFields() {
        with(disposable) {
            clear()
            add(validateInput(ti_email, edt_email_sign_up) { isEmailValid((edt_email_sign_up.text.toString())) })
            add(validateInput(ti_password, edt_password_sign_up) { isPasswordValid(edt_password_sign_up.text.toString()) })
            add(validateInput(ti_confirm_password, edt_confirm_password) { isConfirmPasswordValid(edt_confirm_password.text.toString()) })
        }
    }

    private fun isValidFields(): Boolean {
        return !(ti_email.isErrorEnabled || ti_password.isErrorEnabled || ti_confirm_password.isErrorEnabled)
    }

    private fun isEmailValid(email: String) {
        if (!email.contains("@", true)) {
            ti_email.isErrorEnabled = true
            ti_email.error = getString(R.string.email_validation_error)
        } else {
            ti_email.isErrorEnabled = false
        }
    }

    private fun isPasswordValid(password: String) {
        if (password.length < 4) {
            ti_password.isErrorEnabled = true
            ti_password.error = getString(R.string.password_validation_error)
        } else {
            ti_password.isErrorEnabled = false
        }
    }

    private fun isConfirmPasswordValid(confirmPassword: String) {
        if (confirmPassword != edt_password_sign_up.text.toString()) {
            ti_confirm_password.isErrorEnabled = true
            ti_confirm_password.error = getString(R.string.confirm_password_validation_error)
        } else {
            ti_confirm_password.isErrorEnabled = false
        }
    }

    private fun onRegisterClicked() {
        if (isValidFields() && !edt_email_sign_up.text.isEmpty() && !edt_password_sign_up.text.isEmpty()
                && !edt_confirm_password.text.isEmpty()) {
            presenter.startRegister()
        }
    }

    private fun onBackPressed() {
        val fm = baseActivity?.myFragmentManager
        if (fm?.backStackEntryCount!! > 0)
            fm.popBackStack()
        else
            activity?.finish()
    }
}
