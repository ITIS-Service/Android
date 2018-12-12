package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.Course
import com.itis.itisservice.model.view.Courses
import com.itis.itisservice.model.view.ListCourses
import com.itis.itisservice.mvp.presenter.CourseListPresenter
import com.itis.itisservice.mvp.view.CourseListView
import com.itis.itisservice.ui.view.holder.CourseAdapter
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_course_list.*


class CourseListFragment : BaseFragment(), CourseListView {

    @InjectPresenter
    lateinit var presenter: CourseListPresenter

    private var adapter: CourseAdapter? = null

    private val courseList: MutableList<Courses> = ArrayList()
    private val allCourses: MutableList<Course> = ArrayList()
    private val myCourses: MutableList<Course> = ArrayList()
    private val suggestedCourses: MutableList<Course> = ArrayList()

    companion object {
        fun newInstance(): CourseListFragment {
            val args = Bundle()
            val fragment = CourseListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.setBackArrow(false)
        baseActivity.fragmentOnScreen(this)
        baseActivity.supportActionBar?.show()

        rv_courses.layoutManager = LinearLayoutManager(baseActivity)
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_course_list

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_courses
    }

    override fun showDetails(item: Course) {
        baseActivity.setContent(CourseFragment.newInstance(item), true)
    }

    override fun showCourses(courses: ListCourses) {
        courseList.clear()
        allCourses.clear()
        myCourses.clear()
        suggestedCourses.clear()
        courses.userCourses?.let { myCourses.addAll(it) }
        courses.allCourses?.let { allCourses.addAll(it) }
        if (courses.suggestedCourses?.size == 0) {
            suggestedCourses.add(Course(name = "Нет предложенных курсов"))
        }
        if (courses.userCourses?.size == 0) {
            suggestedCourses.add(Course(name = "Нет моих курсов"))
        }
        courses.suggestedCourses?.let { suggestedCourses.addAll(it) }
        courseList.add(Courses("Мои курсы", myCourses))
        courseList.add(Courses("Все курсы", allCourses))
        courseList.add(Courses("Предложенные курсы", suggestedCourses))

        adapter = CourseAdapter(courseList) { onItemClick(it) }
        rv_courses.adapter = adapter
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
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

    private fun onItemClick(item: Course) {
        presenter.onItemClick(item)
    }
}