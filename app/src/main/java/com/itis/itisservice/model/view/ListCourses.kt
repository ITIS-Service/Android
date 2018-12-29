package com.itis.itisservice.model.view

import com.itis.itisservice.model.course.Course
import com.itis.itisservice.model.course.CourseDetails
import io.realm.annotations.PrimaryKey

open class ListCourses(
        @PrimaryKey
        var id: Long? = null,
        var allCourses: List<Course>? = null,
        var suggestedCourses: List<Course>? = null,
        var userCourses: List<Course>? = null)