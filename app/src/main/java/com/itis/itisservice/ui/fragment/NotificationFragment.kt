package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.View
import com.itis.itisservice.R

class NotificationFragment : BaseFragment() {

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
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_notification

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_notification
    }
}