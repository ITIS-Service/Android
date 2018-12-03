package com.itis.itisservice.ui.view.holder

import android.view.LayoutInflater
import android.view.ViewGroup

import com.itis.itisservice.R
import com.itis.itisservice.model.Course
import com.itis.itisservice.model.Courses
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class CourseAdapter(groups: List<ExpandableGroup<*>>) : ExpandableRecyclerViewAdapter<ListCoursesViewHolder, CourseViewHolder>(groups) {

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): ListCoursesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_courses, parent, false)
        return ListCoursesViewHolder(v)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(v)
    }

    override fun onBindChildViewHolder(holder: CourseViewHolder, flatPosition: Int, group: ExpandableGroup<*>, childIndex: Int) {
        val course = group.items[childIndex] as Course
        holder.bind(course)
    }

    override fun onBindGroupViewHolder(holder: ListCoursesViewHolder, flatPosition: Int, group: ExpandableGroup<*>) {
        val courses = group as Courses
        holder.bind(courses)
    }
}
