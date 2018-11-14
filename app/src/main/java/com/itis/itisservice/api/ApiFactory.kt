package com.itis.itisservice.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {
    companion object {
        private const val ITIS_SERVICE_BASE_URL = ""

        private var userService: UserService? = null

        fun getUserService(): UserService? {
            if (userService == null) {
                userService = buildRetrofit().create(UserService::class.java)
            }
            return userService
        }

        fun recreate() {
            userService = buildRetrofit().create(UserService::class.java)
        }

        private fun buildRetrofit(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(ITIS_SERVICE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
    }
}