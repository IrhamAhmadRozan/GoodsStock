package com.developer.rozan.goodsstock.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.model.Category

class HomeAdapter(private val category: List<Category>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cat = category.get(position)

        holder.ivProductCategoryImage.setImageResource(cat.productCategoryImage)
        holder.tvProductCategoryName.text = cat.productCategoryName
    }

    override fun getItemCount(): Int {
        return category.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProductCategoryImage: ImageView = view.findViewById(R.id.iv_product_category_image)
        val tvProductCategoryName: TextView = view.findViewById(R.id.tv_product_category_name)
    }
}