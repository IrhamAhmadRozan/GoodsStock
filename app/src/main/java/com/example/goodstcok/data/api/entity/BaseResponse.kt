package com.example.goodstcok.data.api.entity

interface BaseResponse<T> {
    fun onSuccess(response: T)
    fun onError(error: String)
}
