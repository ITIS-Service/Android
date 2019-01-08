package com.itis.itisservice.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Point(
        var id: Int? = null,
        var description: String? = null,
        var count: Int? = null,
        var title: String? = null): RealmObject()