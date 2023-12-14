package com.developer.rozan.goodsstock.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.data.local.entity.Category
import com.developer.rozan.goodsstock.listener.RecyclerViewClickListener

class HomeAdapter(private val category: List<Category>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cat = category[position]
        Glide.with(holder.ivProductCategoryImage).load(cat.category_img).into(holder.ivProductCategoryImage)
        holder.tvProductCategoryName.text = cat.name

        holder.cvCategory.setOnClickListener {
            listener?.onItemClicked(it, cat)
        }
    }

    override fun getItemCount(): Int {
        return category.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvCategory: CardView = view.findViewById(R.id.cv_category)
        val ivProductCategoryImage: ImageView = view.findViewById(R.id.iv_product_category_image)
        val tvProductCategoryName: TextView = view.findViewById(R.id.tv_product_category_name)
    }
}