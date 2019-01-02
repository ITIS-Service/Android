package com.itis.itisservice.model

data class UserSettings(
        var id: Int? = 0,
        var courseStatusNotificationEnabled: Boolean? = true,
        var pointsNotificationEnabled: Boolean? = true)