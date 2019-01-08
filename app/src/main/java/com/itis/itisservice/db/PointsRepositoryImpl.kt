package com.itis.itisservice.db

import com.itis.itisservice.model.UserPoints
import io.reactivex.Observable
import io.realm.Realm

class PointsRepositoryImpl : BaseRepository(), PointsRepository {

    override fun addPoints(points: UserPoints, id: Int?): Observable<UserPoints> {
        executeTransaction(Realm.Transaction {
            points.id = id
            it.delete(UserPoints::class.java)
            it.insertOrUpdate(points)
        })
        return Observable.fromCallable { points }
    }

    override fun getPoints(id: Int?): Observable<UserPoints> {
        val points = realm.where(UserPoints::class.java).equalTo("id", id).findFirst()
        return Observable.fromCallable { points }
    }
}