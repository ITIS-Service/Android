package com.itis.itisservice.mvp.view

import com.itis.itisservice.model.course.CourseDetails

interface CourseView: BaseView {
    fun showDescriptionCourse(courseDetails: CourseDetails)
    fun openCourseList()
    fun getCourseId()
}