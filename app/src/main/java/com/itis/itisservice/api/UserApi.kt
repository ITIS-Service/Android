package com.itis.itisservice.api

import com.itis.itisservice.model.*
import com.itis.itisservice.model.view.ListCourses
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("users/login")
    fun signIn(@Body user: User): Single<Response<Void>>

    @POST("users/registration")
    fun signUp(@Body user: User): Single<Response<Void>>

    @GET("users/questions")
    fun questions(@Header("Authorization") token: String?): Observable<List<Question>>

    @POST("users/answers")
    fun sendAnswers(@Header("Authorization") token: String?, @Body answers: Answers): Single<MyResponse>

    @GET("users/courses")
    fun courses(@Header("Authorization") token: String?): Observable<ListCourses>

    @GET("users/courses/{courseID}/details")
    fun course(@Header("Authorization") token: String?, @Path("courseID") id: Int?): Single<Course>

    @POST("users/courses/{courseID}/signUp")
    fun signUpCourse(@Header("Authorization") token: String?, @Path("courseID") id: Int?): Single<Course>

    @GET("users/courses/{courseID}/points")
    fun points(@Header("Authorization") token: String?, @Path("courseID") id: Int?): Observable<UserPoints>

    @POST("users/profile/password/change")
    fun changePassword(@Header("Authorization") token: String?, @Body changePasswordForm: ChangePasswordForm): Single<MyResponse>
}