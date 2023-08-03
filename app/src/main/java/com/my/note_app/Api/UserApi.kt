package com.my.note_app.Api

import com.my.note_app.Model.LoginRequest
import com.my.note_app.Model.UserResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserApi {


    @POST("signup.php")
    @Multipart
    suspend fun Signup(@Part("mobile") mobile: RequestBody?,
                       @Part("name") name: RequestBody?,
                       @Part("gender") gender: RequestBody?,
                       @Part("ispublic") ispublic: RequestBody?,
                       @Part("pass") pass: RequestBody?,
                       @Part("firebase_token") firebase_token: RequestBody?) : Response<UserResponse>

    @POST("login.php")
    @Headers("Content-Type: application/json")
    suspend fun Login(@Body loginRequest: LoginRequest):Response<UserResponse>
}