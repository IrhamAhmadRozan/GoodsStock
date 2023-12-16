package com.developer.rozan.goodsstock.view.product

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
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
import com.developer.rozan.goodsstock.util.extension.gone
import com.developer.rozan.goodsstock.util.extension.visible
import com.developer.rozan.goodsstock.view.add_product.AddProductActivity
import com.developer.rozan.goodsstock.view.detail_product.DetailProductActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity(), OnProductClickListener {

    private lateinit var layoutBack: ConstraintLayout
    private lateinit var titleToolbarBack: TextView
    private lateinit var layoutActionBarBack: LinearLayout
    private lateinit var layoutAction1Back: ConstraintLayout
    private lateinit var swipeProduct: SwipeRefreshLayout
    private lateinit var rvProduct: RecyclerView
    private lateinit var emptyLayout: ConstraintLayout
    private lateinit var tvEmptyDescription: TextView
    private lateinit var btnTryAgain: Button

    private val remoteService = RemoteService()
    private var category: Category? = null
    private val ADD_PRODUCT = 101

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
        emptyLayout = findViewById(R.id.empty_layout)
        tvEmptyDescription = findViewById(R.id.tv_empty_description)
        btnTryAgain = findViewById(R.id.btn_try_again)

        titleToolbarBack.text = category?.name ?: "Back"
        layoutActionBarBack.visible()
        layoutAction1Back.visible()
        layoutBack.setOnClickListener {
            finish()
        }

        layoutAction1Back.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivityForResult(intent, ADD_PRODUCT)
        }

        btnTryAgain.setOnClickListener {
            initFirst()
        }

        initFirst()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PRODUCT) {
            initFirst()
        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initFirst() {
        lifecycleScope.launch {
            delay(DELAY_INTENT)
            rvProduct.visible()
            emptyLayout.gone()
            refresh()
            setUpProduct()
        }
    }

    private fun setUpProduct() {
        val token = UserPref.init.getToken()

        remoteService.getProductListByCategory(
            "Token $token",
            category?.id ?: 0,
            object : BaseResponse<List<Product>> {
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
        rvProduct.layoutManager = GridLayoutManager(this, 2)
        adapter.notifyDataSetChanged()
    }

    private fun getDataCategoryGagal(e: String) {
        Toast.makeText(this, "Error : $e", Toast.LENGTH_SHORT).show()
        rvProduct.gone()
        emptyLayout("There are no products available yet")
    }

    override fun onItemClicked(view: View, product: Product) {
        startActivity(Intent(this, DetailProductActivity::class.java).apply {
            putExtra(Product.PRODUCT, product)
        })
    }

    private fun refresh() {
        swipeProduct.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorWhite
            )
        )
        swipeProduct.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        swipeProduct.setOnRefreshListener {
            initFirst()
            swipeProduct.isRefreshing = false
        }
    }

    private fun emptyLayout(msg: String) {
        emptyLayout.visible()
        tvEmptyDescription.text = msg
    }
}