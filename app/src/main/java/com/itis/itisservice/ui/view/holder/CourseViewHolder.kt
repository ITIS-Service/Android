package com.itis.itisservice.ui.view.holder

import android.view.View

import com.itis.itisservice.model.Course
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import kotlinx.android.synthetic.main.item_course.view.*

class CourseViewHolder(itemView: View) : ChildViewHolder(itemView) {

    fun bind(item: Course, hideDivider: Boolean = false) = with(itemView) {
        tv_course_name.text = item.name
        tv_course_desc.text = item.description
    }
}
