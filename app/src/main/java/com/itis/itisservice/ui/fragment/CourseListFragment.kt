package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.course.CourseDetails
import com.itis.itisservice.model.view.Courses
import com.itis.itisservice.model.ListCourses
import com.itis.itisservice.mvp.presenter.CourseListPresenter
import com.itis.itisservice.mvp.view.CourseListView
import com.itis.itisservice.ui.view.holder.CourseAdapter
import kotlinx.android.synthetic.main.fragment_course_list.*
import org.greenrobot.eventbus.EventBus
import com.itis.itisservice.model.MessageEvent
import com.itis.itisservice.model.course.Course
import com.itis.itisservice.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_internet_error.*
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe


class CourseListFragment : BaseFragment(), CourseListView {

    @InjectPresenter
    lateinit var presenter: CourseListPresenter

    private lateinit var adapter: CourseAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity.setBackArrow(false)
        baseActivity.fragmentOnScreen(this)
        baseActivity.supportActionBar?.show()

        rv_courses.layoutManager = LinearLayoutManager(baseActivity)

        btn_update_courses.setOnClickListener {
            container_courses_empty.visibility = View.GONE
            presenter.loadCourses()
        }
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_course_list

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_courses
    }

    override fun showDetails(item: CourseDetails) {
        //baseActivity.setContent(CourseFragment.newInstance(item), true)
    }

    override fun showCourses(courses: ListCourses) {
        //isEmpty(courses)

        courseList.clear()
        allCourses.clear()
        myCourses.clear()
        suggestedCourses.clear()

        if (courses.userCourses?.size != 0) {
            courses.userCourses?.let { myCourses.addAll(it) }
            courseList.add(Courses("Мои курсы", myCourses))
        }

        if (courses.allCourses?.size != 0) {
            courses.allCourses?.let { allCourses.addAll(it) }
            courseList.add(Courses("Все курсы", allCourses))
        }

        if (courses.suggestedCourses?.size != 0) {
            courses.suggestedCourses?.let { suggestedCourses.addAll(it) }
            courseList.add(Courses("Предложенные курсы", suggestedCourses))
        }

        adapter = CourseAdapter(courseList) { onItemClick(it) }
        for (i in adapter.groups.size - 1 downTo 0) {
            expandGroup(i)
        }

        rv_courses.adapter = adapter
    }

    //todo finish method
    private fun isEmpty(courses: ListCourses) {
        val allCourses = courses.allCourses?.isEmpty() ?: false
        val suggestedCourses = courses.suggestedCourses?.isEmpty() ?: false
        val userCourses = courses.userCourses?.isEmpty() ?: false
        if (allCourses && suggestedCourses && userCourses) {
            Log.d("My tag course list", "$allCourses $suggestedCourses $userCourses")
            container_courses_empty.visibility = View.VISIBLE
        }
    }

    private fun expandGroup(gPos: Int) {
        if (adapter.isGroupExpanded(gPos)) {
            return
        }
        adapter.toggleGroup(gPos)
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionError(error: Throwable) {
//        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
        showProgressError { presenter.loadCourses() }
    }

    override fun showProgress() {
        showProgressBar()
    }

    override fun hideProgress() {
        hideProgressBar()
    }

    override fun onStop() {
        super.onStop()
        hideProgressBar()
    }

    private fun onItemClick(item: Course) {
        baseActivity.setContent(CourseFragment.newInstance(item.name, item.id), true)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        Log.d("www", "eventBus")
        presenter.loadCourses()
    }
}