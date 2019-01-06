package com.itis.itisservice.db

import com.itis.itisservice.model.ListCourses
import io.reactivex.Observable

interface CoursesRepository {
    fun addCourses(courses: ListCourses)

    fun getCourses(): Observable<ListCourses?>
}