package com.itis.itisservice.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.FragmentTransaction
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService

import com.itis.itisservice.R
import com.itis.itisservice.mvp.presenter.SignInPresenter
import com.itis.itisservice.mvp.view.SignInView
import com.itis.itisservice.utils.hide
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_login.*
import com.google.firebase.internal.FirebaseAppHelper.getToken
import com.google.firebase.iid.InstanceIdResult
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.toolbar_layout.*


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

        baseActivity.setBackArrow(false)
        baseActivity.fragmentOnScreen(this)
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
        Toast.makeText(baseActivity, "Неправильно введен логин или пароль", Toast.LENGTH_SHORT).show()
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
//        progressHUD?.show()
        baseActivity.progressBar2?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
//        progressHUD?.dismiss()
        baseActivity.progressBar2?.visibility = View.INVISIBLE
    }

    override fun onStop() {
        super.onStop()
        baseActivity.progressBar2?.visibility = View.INVISIBLE
    }

    override fun enableLoginButton() {
        btn_to_sign_in.isEnabled = true
    }

    override fun disableLoginButton() {
        btn_to_sign_in.isEnabled = false
    }

    override fun onLoginSuccess() {
        //presenter.createSharedPreferences(token)
        baseActivity.setContent(StartQuizFragment.newInstance(), false)
    }

    @SuppressLint("HardwareIds")
    private fun setOnClickListeners() {
        btn_link_to_sign_up.setOnClickListener { baseActivity.setContent(SignUpFragment.newInstance(), true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN) }
        btn_to_sign_in.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(baseActivity) { instanceIdResult ->
                val token = instanceIdResult.token
                presenter.onLoginClicked(email, password, token)
                Log.d("newToken", token)
            }
        }
    }
}

