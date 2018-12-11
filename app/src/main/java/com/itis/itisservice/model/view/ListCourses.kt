package com.itis.itisservice.model.view

import com.itis.itisservice.model.Course
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ListCourses(
        @PrimaryKey
        var id: Long? = null,
        var allCourses: RealmList<Course>? = null,
        var suggestedCourses: RealmList<Course>? = null,
        var userCourses: RealmList<Course>? = null) : RealmObject()