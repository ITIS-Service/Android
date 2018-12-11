package com.itis.itisservice.ui.view.holder

import android.view.View

import com.itis.itisservice.model.view.Courses
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.item_list_courses.view.*
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation


class ListCoursesViewHolder(itemView: View) : GroupViewHolder(itemView) {

    fun bind(item: Courses) = with(itemView) {
        tv_list_courses_name.text = item.title
    }

    override fun expand() {
        animateExpand()
    }

    override fun collapse() {
        animateCollapse()
    }

    private fun animateExpand() = with(itemView) {
        val rotate = RotateAnimation(360f, 180f, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        iv_arrow.animation = rotate
    }

    private fun animateCollapse() = with(itemView) {
        val rotate = RotateAnimation(180f, 360f, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        iv_arrow.animation = rotate
    }
}
