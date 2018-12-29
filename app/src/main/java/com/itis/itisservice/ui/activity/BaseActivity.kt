package com.itis.itisservice.ui.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.TextView

import com.arellomobile.mvp.MvpAppCompatActivity
import com.crashlytics.android.Crashlytics
import com.itis.itisservice.App
import com.itis.itisservice.R
import com.itis.itisservice.tools.QuizManager
import com.itis.itisservice.ui.fragment.BaseFragment
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import javax.inject.Inject
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import com.itis.itisservice.utils.Constants.CHANNEL_DESCRIPTION
import com.itis.itisservice.utils.Constants.CHANNEL_ID
import com.itis.itisservice.utils.Constants.CHANNEL_NAME


abstract class BaseActivity : MvpAppCompatActivity() {

    @Inject
    lateinit var quizManager: QuizManager

    var myFragmentManager: FragmentManager? = null

    @get:LayoutRes
    protected abstract val mainContentLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.applicationComponent.inject(this)
        setContentView(R.layout.activity_base)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            mChannel.description = CHANNEL_DESCRIPTION
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mNotificationManager.createNotificationChannel(mChannel)
        }

        Fabric.with(this, Crashlytics())

        setSupportActionBar(toolbar)

        myFragmentManager = supportFragmentManager

        layoutInflater.inflate(mainContentLayout, main_wrapper)
    }

    override fun onBackPressed() {
        if (quizManager.currentNumber != 0) quizManager.currentNumber--
        super.onBackPressed()
    }

    abstract fun enableBottomNavBar(state: Boolean)

    fun fragmentOnScreen(fragment: BaseFragment) {
        setToolbarTitle(fragment.createToolbarTitle(this))
    }

    fun setToolbarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            toolbar_title.text = title
        }
    }

    fun setBackArrow(enabled: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
            supportActionBar?.setDisplayShowHomeEnabled(enabled)
            toolbar?.setNavigationOnClickListener { onBackPressed() }
        }
    }

    fun setContent(fragment: BaseFragment, addToBackStack: Boolean, animation: Int = 0) {
        val fragmentTransaction = myFragmentManager?.beginTransaction()
        fragmentTransaction?.setTransition(animation)
        if (addToBackStack) {
            fragmentTransaction?.addToBackStack(null)
        }
        fragmentTransaction?.replace(R.id.main_wrapper, fragment)
        fragmentTransaction?.commit()
    }

    fun clearFragmentsStack() {
        myFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
