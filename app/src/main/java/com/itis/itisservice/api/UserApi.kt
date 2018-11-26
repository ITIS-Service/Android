package com.itis.itisservice.api

import com.itis.itisservice.model.Question
import com.itis.itisservice.model.User
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @POST("users/login")
    fun signIn(@Body user: User): Single<Response<Void>>

    @POST("users/registration")
    fun signUp(@Body user: User): Single<Response<Void>>

    @GET("users/questions")
    fun questions(): Observable<List<Question>>
}