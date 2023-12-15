package com.example.goodstcok.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodstcok.R
import com.example.goodstcok.data.local.entity.Category
import com.example.goodstcok.listener.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class HomeAdapter(private val category: List<Category>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var listener: OnCategoryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cat = category[position]

        Picasso.get().invalidate(cat.category_img)

        Picasso.get()
            .load(cat.category_img)
            .placeholder(R.drawable.img)
            .error(R.drawable.img)
            .into(holder.ivProductCategoryImage, object : Callback {
                override fun onSuccess() {
                    // Image loaded successfully
                    Log.d("Picasso", "Image loaded successfully")
                }

                override fun onError(e: Exception?) {
                    // Log the error
                    Log.e("Picasso", "Error loading image", e)
                }
            })

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