package com.itis.itisservice.db

import com.itis.itisservice.model.Profile

interface ProfileRepository {

    fun addProfile(profile: Profile)

    fun getProfile(): Profile?
}