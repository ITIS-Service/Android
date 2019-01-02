package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.itis.itisservice.R
import kotlinx.android.synthetic.main.fragment_internet_error.*

class InternetErrorFragment : BaseFragment() {

    companion object {
        fun newInstance(): InternetErrorFragment {
            val args = Bundle()

            val fragment = InternetErrorFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_update.setOnClickListener { baseActivity.onBackPressed() }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_internet_error

    override fun onCreateToolbarTitle(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}