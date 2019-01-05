package com.itis.itisservice.db

import com.itis.itisservice.model.ListCourses
import io.reactivex.Observable
import io.realm.Realm

class CoursesRepositoryImpl : BaseRepository(), CoursesRepository {

    override fun addCourses(courses: ListCourses) {
        executeTransaction(Realm.Transaction {
            it.copyToRealmOrUpdate(courses)
        })
    }

    override fun getCourses(): Observable<ListCourses?> {
        val listCourses: ListCourses? = realm.where(ListCourses::class.java).findFirst()
        return Observable.fromCallable {
            realm.copyFromRealm(listCourses)
        }
    }
}