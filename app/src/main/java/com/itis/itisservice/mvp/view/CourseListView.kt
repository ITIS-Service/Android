package com.itis.itisservice.mvp.view

import com.itis.itisservice.model.ListCourses

interface CourseListView : BaseView {
    fun showCourses(courses: ListCourses)
}