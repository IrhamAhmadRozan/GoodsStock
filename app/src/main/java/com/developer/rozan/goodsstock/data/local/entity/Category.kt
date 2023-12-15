package com.developer.rozan.goodsstock.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val category_img: String
) : Parcelable {
    companion object {
        const val CATEGORY = "Category"
    }
}
