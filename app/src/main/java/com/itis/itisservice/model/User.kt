package com.itis.itisservice.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User(
        @PrimaryKey
        var id: Int? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var email: String? = null,
        var password: String? = null) : RealmObject()