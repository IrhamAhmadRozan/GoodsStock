package com.developer.rozan.goodsstock.model

import android.os.Parcelable

data class Product(
    val productID: String,
    val productCategoryName: String,
    val productCategoryID: Int,
    val productImage: Int,
    val productQuantity: Int,
    val price: Double
)