package com.itis.itisservice.model.view

import com.itis.itisservice.model.Course
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class Courses(title: String, items: List<Course>) : ExpandableGroup<Course>(title, items)
