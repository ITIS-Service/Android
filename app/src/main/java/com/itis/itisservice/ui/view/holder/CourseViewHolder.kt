package com.itis.itisservice.ui.view.holder

import android.view.View
import android.widget.Toast

import com.itis.itisservice.model.Course
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import kotlinx.android.synthetic.main.item_course.view.*

class CourseViewHolder(itemView: View) : ChildViewHolder(itemView) {

    companion object {
        private const val MAX_LENGTH = 130
        private const val MORE_TEXT = "..."
    }

    fun bind(item: Course, hideDivider: Boolean = false, listener: (Course) -> (Unit)) = with(itemView) {
        tv_course_name.text = item.name
        tv_course_desc.text = item.description
        itemView.setOnClickListener {
            listener(item)
        }
    }

    private fun cutLongDescription(description: String): String {
        return if (description.length < MAX_LENGTH) {
            description
        } else {
            description.substring(0, MAX_LENGTH - MORE_TEXT.length) + MORE_TEXT
        }
    }
}
