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
import retrofit2.http.Query

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