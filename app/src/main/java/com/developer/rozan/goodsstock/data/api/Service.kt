package com.developer.rozan.goodsstock.data.api

import com.developer.rozan.goodsstock.data.api.entity.response.LoginResponse
import com.developer.rozan.goodsstock.data.api.entity.response.LogoutResponse
import com.developer.rozan.goodsstock.data.local.entity.Category
import com.developer.rozan.goodsstock.data.local.entity.Product
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