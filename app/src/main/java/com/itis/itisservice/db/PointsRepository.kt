package com.itis.itisservice.db

import com.itis.itisservice.model.UserPoints
import io.reactivex.Observable

interface PointsRepository {

    fun addPoints(points: UserPoints, id: Int?): Observable<UserPoints>

    fun getPoints(id: Int?): Observable<UserPoints>
}