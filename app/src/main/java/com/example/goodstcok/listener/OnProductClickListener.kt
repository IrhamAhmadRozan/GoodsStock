package com.example.goodstcok.listener

import android.view.View
import com.example.goodstcok.data.local.entity.Product

interface OnProductClickListener {
    fun onItemClicked(view: View, product: Product)
}