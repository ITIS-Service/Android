package com.itis.itisservice.mvp.view

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.itisservice.model.Course
import com.itis.itisservice.model.view.ListCourses

interface CourseListView : BaseView {
    fun showCourses(courses: ListCourses)

    @StateStrategyType(SkipStrategy::class)
    fun showDetails(item: Course)
}