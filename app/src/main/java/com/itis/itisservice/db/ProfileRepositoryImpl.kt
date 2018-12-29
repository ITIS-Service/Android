package com.itis.itisservice.db

import com.itis.itisservice.model.Profile
import io.realm.Realm

class ProfileRepositoryImpl : BaseRepository(), ProfileRepository {

    override fun addProfile(profile: Profile) {
        executeTransaction(Realm.Transaction {
            it.insertOrUpdate(profile)
        })
    }

    override fun getProfile(): Profile? {
        return realm.where(Profile::class.java).findFirst()
    }
}