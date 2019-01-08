package com.itis.itisservice.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserSettings(
        @PrimaryKey
        var id: Int? = 0,
        var courseStatusNotificationEnabled: Boolean? = true,
        var pointsNotificationEnabled: Boolean? = true) : RealmObject()