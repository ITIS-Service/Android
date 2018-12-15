package com.itis.itisservice.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.itis.itisservice.App
import com.itis.itisservice.R
import com.itis.itisservice.ui.activity.StartActivity
import com.itis.itisservice.utils.AppPreferencesHelper
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var sharedPreferences: AppPreferencesHelper

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
        App.applicationComponent.inject(this)

        baseActivity.setBackArrow(false)
        baseActivity.fragmentOnScreen(this)
        baseActivity.supportActionBar?.show()

        btn_sign_out.setOnClickListener { createAlertDialog() }
        btn_account_settings.setOnClickListener { baseActivity.setContent(AccountFragment.newInstance(), true) }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_settings

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_settings
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
                    sharedPreferences.deleteToken()
                    val intent = Intent(baseActivity, StartActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                .setNegativeButton(R.string.no) { dialog, arg -> }
                .show()
    }
}