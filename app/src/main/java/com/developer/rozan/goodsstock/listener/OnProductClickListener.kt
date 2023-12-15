package com.developer.rozan.goodsstock.listener

import android.view.View
import com.developer.rozan.goodsstock.data.local.entity.Product

interface OnProductClickListener {
    fun onItemClicked(view: View, product: Product)
}