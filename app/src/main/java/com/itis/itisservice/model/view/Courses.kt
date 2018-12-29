package com.itis.itisservice.model.view

import com.itis.itisservice.model.course.Course
import com.itis.itisservice.model.course.CourseDetails
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class Courses(title: String, items: List<Course>) : ExpandableGroup<Course>(title, items)
