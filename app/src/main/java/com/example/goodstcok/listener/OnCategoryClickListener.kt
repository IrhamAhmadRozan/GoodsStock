package com.example.goodstcok.listener

import android.view.View
import com.example.goodstcok.data.local.entity.Category

interface OnCategoryClickListener {
    fun onItemClicked(view: View, category: Category)
}