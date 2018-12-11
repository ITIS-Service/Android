package com.itis.itisservice.ui.view.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.itis.itisservice.model.Point
import kotlinx.android.synthetic.main.item_point.view.*

class PointViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Point) = with(itemView) {
        tv_point_title.text = item.title
        tv_point_description.text = item.description
        tv_points.text = "+${item.count}"
    }
}