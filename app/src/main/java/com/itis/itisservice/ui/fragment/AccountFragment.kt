package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.mvp.presenter.AccountPresenter
import com.itis.itisservice.mvp.view.AccountView
import com.itis.itisservice.tools.validation.validateInput
import com.itis.itisservice.utils.hide
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class AccountFragment : BaseFragment(), AccountView {

    private var disposable = CompositeDisposable()

    @InjectPresenter
    lateinit var presenter: AccountPresenter

    companion object {
        fun newInstance(): AccountFragment {
            val args = Bundle()

            val fragment = AccountFragment()
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
        baseActivity.progressBar2?.visibility = View.INVISIBLE
        if (!disposable.isDisposed) disposable.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.setBackArrow(true)
        baseActivity.fragmentOnScreen(this)
        baseActivity.supportActionBar?.show()

        btn_change_password.setOnClickListener {
            onChangePasswordClicked()
        }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_account

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_account
    }

    override fun onValidNewPassword(error: Boolean) {
        if (error) {
            ti_new_password.isErrorEnabled = true
            ti_new_password.error = getString(R.string.password_validation_error)
        } else {
            ti_new_password.isErrorEnabled = false
        }
    }

    override fun onValidConfirmPassword(error: Boolean) {
        if (error) {
            ti_confirm_password.isErrorEnabled = true
            ti_confirm_password.error = getString(R.string.confirm_password_validation_error)
        } else {
            ti_confirm_password.isErrorEnabled = false
        }
    }

    override fun onChangePasswordSuccess() {
        Toast.makeText(baseActivity, "Пароль успешно изменен!", Toast.LENGTH_LONG).show()
        baseActivity.onBackPressed()
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка смены пароля", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        hide(container_account)
        baseActivity.progressBar2?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        baseActivity.progressBar2?.visibility = View.INVISIBLE
    }

    private fun validateFields() {
        with(disposable) {
            clear()
            add(validateInput(ti_new_password, edt_new_password) { presenter.isPasswordValid(edt_new_password.text.toString()) })
            add(validateInput(ti_confirm_password, edt_confirm_password) {
                presenter.isConfirmPasswordValid(edt_new_password.text.toString(), edt_confirm_password.text.toString())
            })
        }
    }

    private fun isEmptyFields(): Boolean {
        return !(!edt_old_password.text?.isEmpty()!! && !edt_new_password.text?.isEmpty()!!
                && !edt_confirm_password.text?.isEmpty()!!)
    }

    private fun isValidFields(): Boolean {
        return !(ti_old_password.isErrorEnabled || ti_new_password.isErrorEnabled || ti_confirm_password.isErrorEnabled)
    }

    private fun onChangePasswordClicked() {
        val newPassword = edt_new_password.text.toString()
        val oldPassword = edt_old_password.text.toString()
        val confirmPassword = edt_confirm_password.text.toString()
        if (isValidFields() && !isEmptyFields() && confirmPassword == newPassword) {
            presenter.changePassword(newPassword, oldPassword)
        }
    }
}