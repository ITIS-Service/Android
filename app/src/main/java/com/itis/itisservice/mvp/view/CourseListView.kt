package com.itis.itisservice.mvp.view

import com.itis.itisservice.model.Course

interface CourseListView : BaseView {
    fun showAllCourses(courses: List<Course>?)
    fun showSuggestedCourses(courses: List<Course>?)
}