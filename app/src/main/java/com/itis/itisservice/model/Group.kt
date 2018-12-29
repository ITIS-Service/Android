package com.itis.itisservice.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Group(
        @PrimaryKey
        var id: Int? = null,
        var name: String? = null,
        var course: Int? = null) : RealmObject()