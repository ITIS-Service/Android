package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.Course
import com.itis.itisservice.model.Courses
import com.itis.itisservice.mvp.presenter.CourseListPresenter
import com.itis.itisservice.mvp.view.CourseListView
import com.itis.itisservice.ui.view.holder.CourseAdapter
import kotlinx.android.synthetic.main.fragment_list_courses.*


class CourseListFragment : BaseFragment(), CourseListView {

    @InjectPresenter
    lateinit var presenter: CourseListPresenter

    private var adapter: CourseAdapter? = null

    private val courseList: MutableList<Courses> = ArrayList()
    private val allCourses: MutableList<Course> = ArrayList()
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

        baseActivity.fragmentOnScreen(this)

        rv_courses.layoutManager = LinearLayoutManager(baseActivity)


        /*allCourses.add(Course(0, "Mобильная разработка (iOS)", "Программист iOS создаёт игры и приложения для устройств " +
                "компании Apple. Разработки для этой операционной системы — самые доходные на мобильном рынке."))
        allCourses.add(Course(1, "Анализ Данных", "Курс предназначен для программистов и " +
                "аналитиков, которых интересует область машинного обучения и анализа данных."))
        allCourses.add(Course(1, "Анализ Данных", "Курс предназначен для программистов и " +
                "аналитиков, которых интересует область машинного обучения и анализа данных."))
        allCourses.add(Course(1, "Анализ Данных", "Курс предназначен для программистов и " +
                "аналитиков, которых интересует область машинного обучения и анализа данных."))

        suggestedCourses.add(Course(3, "Mобильная разработка (Android)", "Разработка под Android — это создание игр и полезных приложений под 80% мобильных устройств. Android — открытая и свободная система, " +
                "настроенная к модернизации и адаптации, она позволяет реализовать самые смелые фантазии программиста."))*/

    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_list_courses

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_courses
    }

    override fun showAllCourses(courses: List<Course>?) {
        allCourses.clear()
        courses?.let { allCourses.addAll(it) }
        courseList.add(Courses("Все курсы", allCourses))
    }

    override fun showSuggestedCourses(courses: List<Course>?) {
        suggestedCourses.clear()
        courses?.let { suggestedCourses.addAll(it) }
        courseList.add(Courses("Предложенные курсы", suggestedCourses))
        adapter = CourseAdapter(courseList)
        rv_courses.adapter = adapter
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
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
}