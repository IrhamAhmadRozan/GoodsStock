package com.example.goodstcok.data.local.entity

data class Product(
    val id: Int,
    val category: Category,
    val name: String,
    val product_img: String,
    val price: Int,
    val quantity: Double
)