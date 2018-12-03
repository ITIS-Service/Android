package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.view.View
import com.itis.itisservice.R

class CourseFragment : BaseFragment() {

    companion object {
        fun newInstance(): CourseFragment {
            val args = Bundle()

            val fragment = CourseFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.supportActionBar?.hide()
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_course

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_course
    }
}