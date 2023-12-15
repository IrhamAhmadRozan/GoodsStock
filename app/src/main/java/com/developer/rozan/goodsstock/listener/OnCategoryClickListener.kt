package com.developer.rozan.goodsstock.listener

import android.view.View
import com.developer.rozan.goodsstock.data.local.entity.Category

interface OnCategoryClickListener {
    fun onItemClicked(view: View, category: Category)
}