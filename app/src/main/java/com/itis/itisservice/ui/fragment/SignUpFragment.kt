package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.text.TextWatcher
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
import android.text.Editable
import android.widget.Toast
import com.itis.itisservice.model.User


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
        edt_email_sign_up.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length == 1) {
                    edt_email_sign_up.setText(edt_email_sign_up.text.toString() + "@stud.kpfu.ru")
                    edt_email_sign_up.setSelection(1)
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                baseActivity?.onBackPressed()
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

    override fun onValidEmail(error: Boolean) {
        if (error) {
            ti_email.isErrorEnabled = true
            ti_email.error = getString(R.string.email_validation_error)
        } else {
            ti_email.isErrorEnabled = false
        }
    }

    override fun onValidPassword(error: Boolean) {
        if (error) {
            ti_password.isErrorEnabled = true
            ti_password.error = getString(R.string.password_validation_error)
        } else {
            ti_password.isErrorEnabled = false
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

    override fun onRegistrationSuccess() {
        baseActivity?.onBackPressed()
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        hide(linear_layout_container_sign_up)
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    private fun validateFields() {
        with(disposable) {
            clear()
            add(validateInput(ti_email, edt_email_sign_up) { presenter.isEmailValid(edt_email_sign_up.text.toString()) })
            add(validateInput(ti_password, edt_password_sign_up) { presenter.isPasswordValid(edt_password_sign_up.text.toString()) })
            add(validateInput(ti_confirm_password, edt_confirm_password) {
                presenter.isConfirmPasswordValid(edt_password_sign_up.text.toString()
                        , edt_confirm_password.text.toString())
            })
        }
    }

    private fun isEmptyFields(): Boolean {
        return !(!edt_email_sign_up.text.isEmpty() && !edt_password_sign_up.text.isEmpty()
                && !edt_confirm_password.text.isEmpty())
    }

    private fun isValidFields(): Boolean {
        return !(ti_email.isErrorEnabled || ti_password.isErrorEnabled || ti_confirm_password.isErrorEnabled)
    }

    private fun onRegisterClicked() {
        val email = edt_email_sign_up.text.toString()
        val password = edt_password_sign_up.text.toString()
        val confirmPassword = edt_confirm_password.text.toString()
        if (isValidFields() && !isEmptyFields() && confirmPassword == password) {
            presenter.startRegister(User(email = email, password = password))
        }
    }
}
