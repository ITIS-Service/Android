package com.itis.itisservice.mvp.view

import com.itis.itisservice.model.Course

interface CourseView: BaseView {
    fun showDetails(course: Course)
}