package com.itis.itisservice.model

import com.itis.itisservice.model.course.Course
import com.itis.itisservice.model.course.CourseDetails
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ListCourses(
        @PrimaryKey
        var id: Long? = 0,
        var allCourses: RealmList<Course>? = null,
        var suggestedCourses: RealmList<Course>? = null,
        var userCourses: RealmList<Course>? = null) : RealmObject()