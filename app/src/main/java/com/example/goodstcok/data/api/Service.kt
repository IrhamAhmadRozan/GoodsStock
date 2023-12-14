package com.example.goodstcok.data.api

import com.example.goodstcok.data.api.entity.response.*
import com.example.goodstcok.data.local.entity.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Service {

    @FormUrlEncoded
    @POST("/rest-auth/login/")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("/goods/category")
    fun getCategoryProduct(@Header("Authorization") token: String): Call<List<Category>>

    @GET("/goods/product")
    fun getAllProduct(@Header("Authorization") token: String): Call<List<Product>>

    @FormUrlEncoded
    @POST("/api/logout")
    fun postLogout(@Header("Authorization") token: String): Call<LogoutResponse>
}