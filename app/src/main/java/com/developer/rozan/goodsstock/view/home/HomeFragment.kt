package com.developer.rozan.goodsstock.view.home

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.data.api.RemoteService
import com.developer.rozan.goodsstock.data.api.entity.BaseResponse
import com.developer.rozan.goodsstock.data.local.entity.Category
import com.developer.rozan.goodsstock.data.local.sharedpref.UserPref
import com.developer.rozan.goodsstock.listener.OnCategoryClickListener
import com.developer.rozan.goodsstock.util.constant.Constant.DELAY_INTENT
import com.developer.rozan.goodsstock.util.extension.gone
import com.developer.rozan.goodsstock.util.extension.invisible
import com.developer.rozan.goodsstock.util.extension.visible
import com.developer.rozan.goodsstock.view.add_category.AddCategoryActivity
import com.developer.rozan.goodsstock.view.add_product.AddProductActivity
import com.developer.rozan.goodsstock.view.product.ProductActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), OnCategoryClickListener {

    private lateinit var titleToolbarMain: TextView
    private lateinit var layoutActionBarMain: LinearLayout
    private lateinit var layoutAction1Main: ConstraintLayout
    private lateinit var swipeHome: SwipeRefreshLayout
    private lateinit var rvCategory: RecyclerView
    private lateinit var emptyLayout: ConstraintLayout
    private lateinit var tvEmptyDescription: TextView
    private lateinit var btnTryAgain: Button

    private val remoteService = RemoteService()
    private val ADD_CATEGORY = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleToolbarMain = view.findViewById(R.id.title_toolbar_main)
        layoutActionBarMain = view.findViewById(R.id.layout_action_bar_main)
        layoutAction1Main = view.findViewById(R.id.layout_action1_main)
        swipeHome = view.findViewById(R.id.swipe_home)
        rvCategory = view.findViewById(R.id.rv_category)
        emptyLayout = view.findViewById(R.id.empty_layout)
        tvEmptyDescription = view.findViewById(R.id.tv_empty_description)
        btnTryAgain = view.findViewById(R.id.btn_try_again)

        titleToolbarMain.text = getString(R.string.app_name)
        layoutActionBarMain.visible()
        layoutAction1Main.setOnClickListener {
            val intent = Intent(requireContext(), AddCategoryActivity::class.java)
            startActivityForResult(intent, ADD_CATEGORY)
        }

        btnTryAgain.setOnClickListener {
            initFirst()
        }

        initFirst()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CATEGORY) {
            initFirst()
        } else {
            Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initFirst() {
        lifecycleScope.launch {
            delay(DELAY_INTENT)
            rvCategory.visible()
            emptyLayout.gone()
            refresh()
            setUpCategory()
        }
    }

    private fun setUpCategory() {
        val token = UserPref.init.getToken()

        remoteService.getCategoryList("Token $token", object : BaseResponse<List<Category>> {
            override fun onSuccess(response: List<Category>) {
                initRecyclerView(response)
            }

            override fun onError(error: String) {
                getDataCategoryGagal(error)
            }
        })
    }

    private fun initRecyclerView(category: List<Category>) {
        rvCategory.layoutManager = LinearLayoutManager(context)
        val adapter = HomeAdapter(category)
        adapter.listener = this
        rvCategory.adapter = adapter
        rvCategory.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.notifyDataSetChanged()
    }

    private fun getDataCategoryGagal(e: String) {
        Toast.makeText(activity, "Error : $e", Toast.LENGTH_SHORT).show()
        rvCategory.gone()
        emptyLayout("There are no categories available yet")
    }

    override fun onItemClicked(view: View, category: Category) {
        startActivity(Intent(activity, ProductActivity::class.java).apply {
            putExtra(Category.CATEGORY, category)
        })
    }

    private fun refresh() {
        swipeHome.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorWhite
            )
        )
        swipeHome.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimary
            )
        )
        swipeHome.setOnRefreshListener {
            initFirst()
            swipeHome.isRefreshing = false
        }
    }

    private fun emptyLayout(msg: String) {
        emptyLayout.visible()
        tvEmptyDescription.text = msg
    }
}