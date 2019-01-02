package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.UserSettings
import com.itis.itisservice.mvp.presenter.NotificationPresenter
import com.itis.itisservice.mvp.view.NotificationView
import kotlinx.android.synthetic.main.fragment_notification.*

class NotificationFragment : BaseFragment(), NotificationView, CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
        when (p0) {
            switch_status -> {
                presenter.setStatusNotification(isChecked)
                presenter.changeNotifications(
                        UserSettings(courseStatusNotificationEnabled = isChecked, pointsNotificationEnabled = switch_points.isChecked))
            }
            switch_points -> {
                presenter.setPointsNotification(isChecked)
                presenter.changeNotifications(
                        UserSettings(courseStatusNotificationEnabled = switch_status.isChecked, pointsNotificationEnabled = isChecked))
            }
        }
    }

    override fun onCodeInvalid() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @InjectPresenter
    lateinit var presenter: NotificationPresenter

    companion object {
        fun newInstance(): NotificationFragment {
            val args = Bundle()

            val fragment = NotificationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.setBackArrow(true)
        baseActivity.fragmentOnScreen(this)
        baseActivity.supportActionBar?.show()

        switch_status.isChecked = presenter.getStatusNotification()
        switch_points.isChecked = presenter.getPointsNotification()
        switch_status.setOnCheckedChangeListener(this)
        switch_points.setOnCheckedChangeListener(this)
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_notification

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_notification
    }
}