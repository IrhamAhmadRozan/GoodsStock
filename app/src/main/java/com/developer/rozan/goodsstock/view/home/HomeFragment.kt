package com.developer.rozan.goodsstock.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.data.api.RemoteService
import com.developer.rozan.goodsstock.data.api.entity.BaseResponse
import com.developer.rozan.goodsstock.data.local.entity.Category
import com.developer.rozan.goodsstock.data.local.sharedpref.UserPref
import com.developer.rozan.goodsstock.listener.RecyclerViewClickListener
import com.developer.rozan.goodsstock.view.dashboard.DashboardActivity
import com.developer.rozan.goodsstock.view.product.ProductActivity
import org.json.JSONException

class HomeFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var rvCategory : RecyclerView

    private val remoteService = RemoteService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCategory = view.findViewById(R.id.rv_cetegory)

        val token = UserPref.init.getToken()

        remoteService.getCategoryList("Token " + token, object : BaseResponse<List<Category>> {
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
    }

    private fun getDataCategoryGagal(e: String) {
        Toast.makeText(activity, "Error : " + e, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(view: View, category: Category) {
        startActivity(Intent(activity, ProductActivity::class.java))
    }
}