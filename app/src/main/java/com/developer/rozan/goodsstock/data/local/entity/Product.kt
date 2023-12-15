package com.developer.rozan.goodsstock.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val category: Category,
    val name: String,
    val product_img: String,
    val price: Int,
    val quantity: Int,
    val description: String
) : Parcelable {
    companion object {
        const val PRODUCT = "Product"
    }
}