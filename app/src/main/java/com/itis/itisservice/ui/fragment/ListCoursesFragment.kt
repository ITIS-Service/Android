package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.itis.itisservice.R
import com.itis.itisservice.model.Course
import com.itis.itisservice.model.ListCourses
import com.itis.itisservice.ui.view.holder.CourseAdapter
import kotlinx.android.synthetic.main.fragment_list_courses.*


class ListCoursesFragment : BaseFragment() {

    private var adapter: CourseAdapter? = null

    companion object {
        fun newInstance(): ListCoursesFragment {
            val args = Bundle()
            val fragment = ListCoursesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.fragmentOnScreen(this)

        rv_courses.layoutManager = LinearLayoutManager(baseActivity)

        val courses: ArrayList<ListCourses> = ArrayList()

        val listCourses1: ArrayList<Course> = ArrayList()
        val listCourses2: ArrayList<Course> = ArrayList()

        listCourses1.add(Course(0, "Mобильная разработка (iOS)", "Программист iOS создаёт игры и приложения для устройств " +
                "компании Apple. Разработки для этой операционной системы — самые доходные на мобильном рынке."))
        listCourses1.add(Course(1, "Анализ Данных", "Курс предназначен для программистов и " +
                "аналитиков, которых интересует область машинного обучения и анализа данных."))
        listCourses1.add(Course(1, "Анализ Данных", "Курс предназначен для программистов и " +
                "аналитиков, которых интересует область машинного обучения и анализа данных."))
        listCourses1.add(Course(1, "Анализ Данных", "Курс предназначен для программистов и " +
                "аналитиков, которых интересует область машинного обучения и анализа данных."))

        listCourses2.add(Course(3, "Mобильная разработка (Android)", "Разработка под Android — это создание игр и полезных приложений под 80% мобильных устройств. Android — открытая и свободная система, " +
                "настроенная к модернизации и адаптации, она позволяет реализовать самые смелые фантазии программиста."))

        courses.add(ListCourses("Предложенные курсы", listCourses1))
        courses.add(ListCourses("Все курсы", listCourses2))

        adapter = CourseAdapter(courses)
        rv_courses.adapter = adapter
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_list_courses

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_courses
    }
}