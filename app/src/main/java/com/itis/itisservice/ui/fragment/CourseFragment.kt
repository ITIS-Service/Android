package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.Course
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
import com.itis.itisservice.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*


class CourseFragment : BaseFragment(), CourseView {

    @InjectPresenter
    lateinit var presenter: CoursePresenter

    private var course: Course? = null

    companion object {
        fun newInstance(item: Course): CourseFragment {
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

        course = arguments?.getParcelable(COURSE_ITEM)
        course?.let { showDescriptionCourse(it) }

        course?.name?.let { baseActivity.setToolbarTitle(it) }

        btn_sign_up_for_course.setOnClickListener { createAlertDialog() }
        btn_course_progress.setOnClickListener { baseActivity.setContent(ProgressFragment.newInstance(course?.id), true) }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_course

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_course
    }

    override fun showDetails(course: Course) {
        baseActivity.setContent(CourseFragment.newInstance(course), false)
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
        baseActivity.progressBar2?.visibility = View.GONE
    }

    private fun showDescriptionCourse(course: Course) {
        if (!course.signUpOpen) {
            showStatusBlock()
        } else {
            hideStatusBlock()
        }

        val teacher = course.teacher
        val name = teacher?.firstName + " " + teacher?.lastName
        val link = teacher?.link

        tv_course_desc.text = course.description
        tv_course_place.text = course.place
        tv_teacher_name.text = name

        addLinkTeacher(link)
    }

    private fun hideStatusBlock() {
        btn_sign_up_for_course.visibility = View.VISIBLE
        btn_course_progress.visibility = View.GONE
        tv_course_status.visibility = View.GONE
        tv_status.visibility = View.GONE
        status_divider.visibility = View.GONE
    }

    private fun showStatusBlock() {
        btn_sign_up_for_course.visibility = View.GONE
        btn_course_progress.visibility = View.VISIBLE
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
                .setTitle(R.string.dialog_title)
                .setMessage("Подтверждение записи на курс " + course?.name)
                .setCancelable(true)
                .setPositiveButton(R.string.ok) { dialog, arg1 ->
                    presenter.signUp(course?.id)
                }
                .setNegativeButton(R.string.dismiss) { dialog, arg -> }
                .show()
    }
}