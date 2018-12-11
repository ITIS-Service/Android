package com.itis.itisservice.ui.view.holder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.itis.itisservice.R
import com.itis.itisservice.model.Point

class PointAdapter : RecyclerView.Adapter<PointViewHolder>() {

    private val points: MutableList<Point> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewTipe: Int): PointViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_point, parent, false)
        return PointViewHolder(v)
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return points.size
    }

    private fun getItem(position: Int): Point {
        return points[position]
    }

    private fun addItems(newItems: List<Point>) {
        points.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItem(point: Point) {
        points.add(point)
        notifyDataSetChanged()
    }

    fun setItems(items: List<Point>) {
        clearList()
        addItems(items)
    }

    private fun clearList() {
        points.clear()
    }
}