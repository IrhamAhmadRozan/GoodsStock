package com.example.goodstcok.data.api

import com.example.goodstcok.data.api.entity.response.*
import com.example.goodstcok.data.local.entity.*
import retrofit2.Call
import retrofit2.http.*

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
    fun getProductByCategory(
        @Header("Authorization") token: String,
        @Query("cid") categoryId: Int
    ): Call<List<Product>>

    @FormUrlEncoded
    @POST("/goods/add-category")
    fun uploadCategory(
        @Header("X-CSRFToken") csrf: String,
        @Field("name") name: String
    ): Call<LogoutResponse>

    @FormUrlEncoded
    @POST("/goods/add-product")
    fun uploadProduct(
        @Header("X-CSRFToken") csrf: String,
        @Field("name") username: String,
        @Field("price") price: Int,
        @Field("quantity") quantity: Int,
        @Field("description") decription: String
    ): Call<LogoutResponse>

    @FormUrlEncoded
    @POST("/api/logout")
    fun postLogout(@Header("Authorization") token: String): Call<LoginResponse>
}