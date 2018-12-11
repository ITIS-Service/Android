package com.itis.itisservice.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Answer(
        @PrimaryKey
        var id: Int? = null,
        var title: String? = null) : RealmObject()

