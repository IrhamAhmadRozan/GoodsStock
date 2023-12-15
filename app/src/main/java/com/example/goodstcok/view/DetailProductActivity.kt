package com.example.goodstcok.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.goodstcok.R
import com.example.goodstcok.data.local.entity.Product
import com.example.goodstcok.utils.extension.formatCurrency

class DetailProductActivity : AppCompatActivity() {

    private lateinit var layoutBack : ConstraintLayout
    private lateinit var titleToolbarBack : TextView
    private lateinit var ivDetailProductImage : ImageView
    private lateinit var tvDetailProductName : TextView
    private lateinit var tvDetailProductPrice : TextView
    private lateinit var tvDetailProductQuantity : TextView
    private lateinit var tvDetailProductDecription : TextView

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        if (intent.getParcelableExtra<Parcelable>(Product.PRODUCT) != null)
            product = intent.getParcelableExtra(Product.PRODUCT)

        layoutBack = findViewById(R.id.layout_back)
        titleToolbarBack = findViewById(R.id.title_toolbar_back)

        ivDetailProductImage = findViewById(R.id.iv_detail_product_image)
        tvDetailProductName = findViewById(R.id.tv_detail_product_name)
        tvDetailProductPrice = findViewById(R.id.tv_detail_product_price)
        tvDetailProductQuantity = findViewById(R.id.tv_detail_product_quantity)
        tvDetailProductDecription = findViewById(R.id.tv_detail_product_decription)

        titleToolbarBack.text = "Product Detail"
        Glide.with(ivDetailProductImage).load(product?.product_img).into(ivDetailProductImage)
        tvDetailProductName.text = product?.name
        tvDetailProductPrice.text = product?.price.toString().formatCurrency()
        tvDetailProductQuantity.text = product?.quantity.toString() + "pcs"
        tvDetailProductDecription.text = product?.description
        if (product?.description.equals("")) {
            tvDetailProductDecription.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        }

        layoutBack.setOnClickListener {
            finish()
        }
    }
}