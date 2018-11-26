package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter

import com.itis.itisservice.R
import com.itis.itisservice.mvp.presenter.SignInPresenter
import com.itis.itisservice.mvp.view.SignInView
import com.itis.itisservice.ui.activity.BaseActivity
import com.itis.itisservice.utils.hide
import kotlinx.android.synthetic.main.fragment_login.*

class SignInFragment : BaseFragment(), SignInView {

    @InjectPresenter
    lateinit var presenter: SignInPresenter

    companion object {

        fun newInstance(): SignInFragment {
            val args = Bundle()
            val fragment = SignInFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.setBackArrow(false)
        baseActivity?.fragmentOnScreen(this)
        setOnClickListeners()

        edt_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (text.length == 1) {
                    edt_email.setText(edt_email.text.toString() + "@stud.kpfu.ru")
                    edt_email.setSelection(1)
                }
                presenter.onEmailTextChanged(text)
            }
        })

        edt_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence, start: Int,
                                       before: Int, count: Int) {
                presenter.onPasswordTextChanged(text)
            }
        })
    }

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_sign_in
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_login

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
    }

    override fun forceUpdateEmailPassword() {
        edt_email.forceUpdateState()
        edt_password.forceUpdateState()
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        hide(linear_layout_container_sign_in)
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun enableLoginButton() {
        btn_to_sign_in.isEnabled = true
    }

    override fun disableLoginButton() {
        btn_to_sign_in.isEnabled = false
    }

    override fun onLoginSuccess(token: String?) {
        presenter.createSharedPreferences(token)
        baseActivity?.setContent(StartQuizFragment.newInstance(), false)
    }

    private fun setOnClickListeners() {
        btn_link_to_sign_up.setOnClickListener { baseActivity?.setContent(SignUpFragment.newInstance(), true) }
        btn_to_sign_in.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            presenter.onLoginClicked(email, password)
        }
    }
}

