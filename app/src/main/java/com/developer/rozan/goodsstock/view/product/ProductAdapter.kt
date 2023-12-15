package com.developer.rozan.goodsstock.view.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.data.local.entity.Product
import com.developer.rozan.goodsstock.listener.OnProductClickListener
import com.developer.rozan.goodsstock.util.extension.formatCurrency

class ProductAdapter(private val product: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var listener: OnProductClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_goods, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prod = product[position]
        Glide.with(holder.ivProductImage).load(prod.product_img).into(holder.ivProductImage)
        holder.tvProductName.text = prod.name
        holder.tvProductPrice.text = prod.price.toString().formatCurrency()

        holder.cvProduct.setOnClickListener {
            listener?.onItemClicked(it, prod)
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvProduct: CardView = view.findViewById(R.id.cv_product)
        val ivProductImage: ImageView = view.findViewById(R.id.iv_product_image)
        val tvProductName: TextView = view.findViewById(R.id.tv_product_name)
        val tvProductPrice: TextView = view.findViewById(R.id.tv_product_price)
    }
}