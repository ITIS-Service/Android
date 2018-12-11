package com.itis.itisservice.mvp.view

import com.itis.itisservice.model.UserPoints

interface ProgressView : BaseView {
    fun getCourseId()
    fun showPoints(points: UserPoints)
}