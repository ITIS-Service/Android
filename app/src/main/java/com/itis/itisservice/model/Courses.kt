package com.itis.itisservice.model

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class Courses(title: String, items: List<Course>) : ExpandableGroup<Course>(title, items)
