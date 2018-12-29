package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.course.CourseDetails
import com.itis.itisservice.mvp.presenter.CoursePresenter
import com.itis.itisservice.mvp.view.CourseView
import com.itis.itisservice.utils.Constants.COURSE_ITEM
import kotlinx.android.synthetic.main.fragment_course.*
import android.widget.TextView
import android.text.Spanned
import android.content.Intent
import android.net.Uri
import android.text.style.ClickableSpan
import android.text.SpannableString
import android.app.AlertDialog
import com.itis.itisservice.model.course.CourseStatus
import kotlinx.android.synthetic.main.activity_base.*
import com.itis.itisservice.model.MessageEvent
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.greenrobot.eventbus.EventBus


class CourseFragment : BaseFragment(), CourseView {

    @InjectPresenter
    lateinit var presenter: CoursePresenter

    private var courseDetails: CourseDetails? = null

    companion object {
        fun newInstance(item: CourseDetails): CourseFragment {
            val args = Bundle()
            args.putParcelable(COURSE_ITEM, item)

            val fragment = CourseFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.supportActionBar?.show()
        baseActivity.setBackArrow(true)

        courseDetails = arguments?.getParcelable(COURSE_ITEM)
        courseDetails?.let { showDescriptionCourse(it) }

        courseDetails?.course?.name?.let { baseActivity.setToolbarTitle(it) }

        setOnClickListener()
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_course

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_course
    }

    override fun showDetails(course: CourseDetails) {
        baseActivity.clearFragmentsStack()
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка получения данных", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        baseActivity.progressBar2?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        EventBus.getDefault().post(MessageEvent())
        baseActivity.progressBar2?.visibility = View.INVISIBLE
    }

    private fun setOnClickListener() {
        btn_course_sign_out.setOnClickListener {
            createAlertDialog(R.string.dialog_title_sign_out, "Подтверждение отписки от курса ", true)
        }
        btn_sign_up_for_course.setOnClickListener {
            createAlertDialog(R.string.dialog_title_sign_up, "Подтверждение записи на курс ")
        }
        btn_course_progress.setOnClickListener { baseActivity.setContent(ProgressFragment.newInstance(courseDetails?.id), true) }
    }

    private fun showDescriptionCourse(courseDetails: CourseDetails) {
        when (courseDetails.userCourseStatus) {
            CourseStatus.ACCEPTED -> showStatusBlock()
            CourseStatus.WAITING -> showStatusBlock(true)
            else -> {
                hideStatusBlock()
            }
        }
        if (courseDetails.signUpOpen && courseDetails.userCourseStatus == null)
            hideStatusBlock(true)

        val teacher = courseDetails.teacher
        val name = teacher?.firstName + " " + teacher?.lastName
        val link = teacher?.link

        tv_course_desc.text = courseDetails.course?.description
        tv_course_place.text = courseDetails.place
        tv_teacher_name.text = name
        tv_course_status.text = courseDetails.userCourseStatus?.status
        val time = StringBuffer()
        courseDetails.dayTimes?.get(0)?.times?.forEach {
            time.append("$it, ")
        }
        time.delete(time.length - 2, time.length)
        tv_course_dayTime.text = time

        addLinkTeacher(link)
    }

    private fun hideStatusBlock(signUpOpen: Boolean = false) {
        btn_sign_up_for_course.visibility = if (signUpOpen) View.VISIBLE else View.GONE
        btn_course_progress.visibility = View.GONE
        tv_course_status.visibility = View.GONE
        tv_status.visibility = View.GONE
        status_divider.visibility = View.GONE
    }

    private fun showStatusBlock(signOutOpen: Boolean = false) {
        btn_sign_up_for_course.visibility = View.GONE
        if (signOutOpen) {
            btn_course_sign_out.visibility = View.VISIBLE
            btn_course_progress.visibility = View.GONE
        } else {
            btn_course_progress.visibility = View.VISIBLE
            btn_course_sign_out.visibility = View.GONE
        }
        tv_course_status.visibility = View.VISIBLE
        tv_status.visibility = View.VISIBLE
        status_divider.visibility = View.VISIBLE
    }

    private fun addLinkTeacher(link: String?) {
        val spannableString = SpannableString(getString(R.string.messageWithSpannableLink))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            }
        }
        spannableString.setSpan(clickableSpan, 0,
                spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_teacher_link.setText(spannableString, TextView.BufferType.SPANNABLE)
        tv_teacher_link.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun createAlertDialog(title: Int, message: String, signOutOpen: Boolean = false) {
        /*iOSDialogBuilder(baseActivity)
                .setTitle(getString(R.string.dialog_title))
                .setSubtitle(getString(R.string.dialog_subtitle) + " " + courseDetails?.name)
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
                .setTitle(title)
                .setMessage("$message \"${courseDetails?.course?.name}\"")
                .setCancelable(true)
                .setPositiveButton(R.string.ok) { dialog, arg1 ->
                    if (signOutOpen) {
                        presenter.signOut(courseDetails?.id)
                    } else {
                        presenter.signUp(courseDetails?.id)
                    }
                }
                .setNegativeButton(R.string.dismiss) { dialog, arg -> }
                .show()
    }
}