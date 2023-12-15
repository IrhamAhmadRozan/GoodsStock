package com.example.goodstcok.data.api

import com.example.goodstcok.data.api.entity.BaseResponse
import com.example.goodstcok.data.api.entity.response.LoginResponse
import com.example.goodstcok.data.api.entity.response.LogoutResponse
import com.example.goodstcok.data.local.entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://e-vote.zaws.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: Service = retrofit.create(Service::class.java)

    fun loginUser(username: String, password: String, callback: BaseResponse<String>) {
        val call: Call<LoginResponse> = service.login(username, password)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.key
                    if (token != null) {
                        callback.onSuccess(token)
                    } else {
                        callback.onError("Token is null")
                    }
                } else {
                    callback.onError("Login failed, token not received")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onError(t.message ?: "An error occurred")
            }
        })
    }

    fun logoutUser(token: String, callback: BaseResponse<String>) {
        val call: Call<LoginResponse> = service.postLogout(token)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.key
                    if (token != null) {
                        callback.onSuccess(token)
                    } else {
                        callback.onError("Token is null")
                    }
                } else {
                    callback.onError("Logout failed, logout not successfully")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onError(t.message ?: "An error occurred")
            }
        })
    }

    fun getCategoryList(token: String, callback: BaseResponse<List<Category>>) {
        val call = service.getCategoryProduct(token)

        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (!data.isNullOrEmpty()) {
                        callback.onSuccess(data)
                    } else {
                        callback.onError("Data Category is null")
                    }
                } else {
                    callback.onError("Get Data Category failed, data category not received")
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                callback.onError(t.message ?: "An error occurred")
            }
        })
    }

    fun getProductListByCategory(
        token: String,
        categoryId: Int,
        callback: BaseResponse<List<Product>>
    ) {
        val call = service.getProductByCategory(token, categoryId)

        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (!data.isNullOrEmpty()) {
                        callback.onSuccess(data)
                    } else {
                        callback.onError("Data Product is null")
                    }
                } else {
                    callback.onError("Get Data Product failed, data product not received")
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback.onError(t.message ?: "An error occurred")
            }
        })
    }
}