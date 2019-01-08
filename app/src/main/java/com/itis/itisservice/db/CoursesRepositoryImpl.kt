package com.itis.itisservice.db

import com.itis.itisservice.model.ListCourses
import io.reactivex.Observable
import io.realm.Realm

class CoursesRepositoryImpl : BaseRepository(), CoursesRepository {

    override fun addCourses(courses: ListCourses): Observable<ListCourses> {
        executeTransaction(Realm.Transaction {
            it.delete(ListCourses::class.java)
            it.insertOrUpdate(courses)
        })
        return Observable.fromCallable { courses }
    }

    override fun getCourses(): Observable<ListCourses?> {
        val listCourses: ListCourses? = realm.where(ListCourses::class.java).findFirst()
        return Observable.fromCallable {
            listCourses
        }
    }
}