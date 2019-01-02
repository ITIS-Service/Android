package com.itis.itisservice.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.Profile
import com.itis.itisservice.mvp.presenter.SettingsPresenter
import com.itis.itisservice.mvp.view.SettingsView
import com.itis.itisservice.ui.activity.StartActivity
import com.itis.itisservice.utils.Constants.QUIZ_IS_AGAIN
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(), SettingsView {

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    companion object {
        fun newInstance(): SettingsFragment {
            val args = Bundle()

            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity.setBackArrow(false)
        baseActivity.fragmentOnScreen(this)
        baseActivity.supportActionBar?.show()

        showProfile(presenter.getProfile())
        setOnClickListener()
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_settings

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_settings
    }

    private fun showProfile(profile: Profile?) {
        val userName = "${profile?.firstName} ${profile?.lastName}"
        tv_user_name.text = userName
        tv_user_email.text = "${profile?.email}"
        tv_user_group.text = "${profile?.group?.name}"
        tv_user_course.text = profile?.group?.course.toString()
    }

    override fun unregisterSuccess() {
        val intent = Intent(baseActivity, StartActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка выхода из приложения", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        showProgressBar()
    }

    override fun hideProgress() {
        hideProgressBar()
    }

    private fun createAlertDialog() {
        /*iOSDialogBuilder(baseActivity)
                .setTitle(getString(R.string.dialog_title))
                .setSubtitle(getString(R.string.dialog_subtitle) + " " + course?.name)
                .setBoldPositiveLabel(false)
                .setCancelable(false)
                .setPositiveListener(getString(R.string.ok)) { dialog ->
                    Toast.makeText(baseActivity, "Clicked!", Toast.LENGTH_LONG).show()
                    showStatusBlock()
                    dialog.dismiss()
                }
                .setNegativeListener(getString(R.string.dismiss)) { dialog -> dialog.dismiss() }
                .build().show()*/

        AlertDialog.Builder(baseActivity, R.style.AlertDialogCustom)
                .setMessage("Вы действительно хотите выйти?")
                .setCancelable(true)
                .setPositiveButton(R.string.yes) { dialog, arg1 ->
                    presenter.unregisterDevice()
                }
                .setNegativeButton(R.string.no) { dialog, arg -> }
                .show()
    }

    private fun createSlideTransaction(fragment: BaseFragment) {
        // todo solve problem with hiding toolbar
        val transaction = baseActivity.myFragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right)
        transaction?.addToBackStack(null)
        transaction?.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
        transaction?.add(R.id.main_wrapper, fragment)?.commit()
    }

    private fun setOnClickListener() {
        btn_sign_out.setOnClickListener { createAlertDialog() }
        btn_account_settings.setOnClickListener { baseActivity.setContent(AccountFragment.newInstance(), true) }
        btn_quiz_settings.setOnClickListener {
            //baseActivity.setContent(StartQuizFragment.newInstance(true), true)
            val intent = Intent(baseActivity, StartActivity::class.java)
            intent.putExtra(QUIZ_IS_AGAIN, true)
            startActivity(intent)
        }
        btn_notification_settings.setOnClickListener {
            baseActivity.setContent(NotificationFragment.newInstance(),
                    true)
        }
    }
}