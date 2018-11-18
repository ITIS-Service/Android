package com.itis.itisservice.ui.activity

import android.os.Bundle

import com.itis.itisservice.R
import com.itis.itisservice.model.ListCourses
import com.itis.itisservice.ui.fragment.ListCoursesFragment
import com.itis.itisservice.ui.fragment.SignInFragment

class MainActivity : BaseActivity() {

    override val mainContentLayout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent(SignInFragment.newInstance(), false)
    }
}
