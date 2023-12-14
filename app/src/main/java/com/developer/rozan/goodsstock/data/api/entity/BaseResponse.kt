package com.developer.rozan.goodsstock.data.api.entity

interface BaseResponse<T> {
    fun onSuccess(response: T)
    fun onError(error: String)
}
