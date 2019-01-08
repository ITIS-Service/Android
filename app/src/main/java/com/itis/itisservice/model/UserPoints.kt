package com.itis.itisservice.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserPoints(
        @PrimaryKey
        var id: Int? = null,
        var points: RealmList<Point>? = null,
        var total: Int = 0) : RealmObject()