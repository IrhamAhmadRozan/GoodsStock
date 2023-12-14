package com.example.goodstcok.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodstcok.R
import com.example.goodstcok.data.api.RemoteService
import com.example.goodstcok.data.api.entity.BaseResponse
import com.example.goodstcok.data.local.entity.Category
import com.example.goodstcok.data.local.sharedpref.UserPref
import com.example.goodstcok.listener.RecyclerViewClickListener


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
    }

    private fun getDataCategoryGagal(e: String) {
        Toast.makeText(activity, "Error : $e", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(view: View, category: Category) {
        startActivity(Intent(activity, ProductActivity::class.java))
    }
}