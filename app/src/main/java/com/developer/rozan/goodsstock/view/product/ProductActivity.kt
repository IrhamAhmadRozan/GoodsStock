package com.developer.rozan.goodsstock.view.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.data.api.RemoteService
import com.developer.rozan.goodsstock.data.api.entity.BaseResponse
import com.developer.rozan.goodsstock.data.local.entity.Category
import com.developer.rozan.goodsstock.data.local.entity.Product
import com.developer.rozan.goodsstock.data.local.sharedpref.UserPref
import com.developer.rozan.goodsstock.listener.OnProductClickListener
import com.developer.rozan.goodsstock.util.constant.Constant.DELAY_INTENT
import com.developer.rozan.goodsstock.util.extension.visible
import com.developer.rozan.goodsstock.view.add_product.AddProductActivity
import com.developer.rozan.goodsstock.view.detail_product.DetailProductActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity(), OnProductClickListener {

    private lateinit var layoutBack : ConstraintLayout
    private lateinit var titleToolbarBack : TextView
    private lateinit var layoutActionBarBack : LinearLayout
    private lateinit var layoutAction1Back : ConstraintLayout
    private lateinit var swipeProduct : SwipeRefreshLayout
    private lateinit var rvProduct : RecyclerView

    private val remoteService = RemoteService()
    private var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        UserPref.init = UserPref(this)
        if (intent.getParcelableExtra<Parcelable>(Category.CATEGORY) != null)
            category = intent.getParcelableExtra(Category.CATEGORY)

        layoutBack = findViewById(R.id.layout_back)
        titleToolbarBack = findViewById(R.id.title_toolbar_back)
        layoutActionBarBack = findViewById(R.id.layout_action_bar_back)
        layoutAction1Back = findViewById(R.id.layout_action1_back)
        swipeProduct = findViewById(R.id.swipe_product)
        rvProduct = findViewById(R.id.rv_product)

        titleToolbarBack.text = category?.name ?: "Back"
        layoutActionBarBack.visible()
        layoutBack.setOnClickListener {
            finish()
        }

        layoutAction1Back.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }

        initFirst()
    }

    private fun initFirst() {
        lifecycleScope.launch {
            delay(DELAY_INTENT)
            refresh()
            setUpProduct()
        }
    }

    private fun refresh() {
        swipeProduct.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(this,
                R.color.colorWhite
            )
        )
        swipeProduct.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        swipeProduct.setOnRefreshListener {
            initFirst()
            swipeProduct.isRefreshing = false
        }
    }

    private fun setUpProduct() {
        val token = UserPref.init.getToken()

        remoteService.getProductListByCategory("Token $token", category?.id ?: 0, object : BaseResponse<List<Product>> {
            override fun onSuccess(response: List<Product>) {
                initRecyclerView(response)
            }

            override fun onError(error: String) {
                getDataCategoryGagal(error)
            }

        })
    }

    private fun initRecyclerView(product: List<Product>) {
        rvProduct.layoutManager = LinearLayoutManager(this)
        val adapter = ProductAdapter(product)
        adapter.listener = this
        rvProduct.adapter = adapter
    }

    private fun getDataCategoryGagal(e: String) {
        Toast.makeText(this, "Error : $e", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(view: View, product: Product) {
        startActivity(Intent(this, DetailProductActivity::class.java).apply {
            putExtra(Product.PRODUCT, product)
        })
    }
}