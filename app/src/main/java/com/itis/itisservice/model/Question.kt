package com.itis.itisservice.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Question (
        @PrimaryKey
        var id: Int? = null,
        var title: String? = null,
        var answers: RealmList<Answer>? = null) : RealmObject()