package com.developer.rozan.goodsstock.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.model.Category

class HomeFragment : Fragment() {

    private lateinit var rvCategory : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCategory = view.findViewById(R.id.rv_cetegory)

        rvCategory.layoutManager = LinearLayoutManager(view.context)

        val data = ArrayList<Category>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..4) {
            data.add(Category(i, "Category $i", R.drawable.ic_add))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = HomeAdapter(data)

        // Setting the Adapter with the recyclerview
        rvCategory.adapter = adapter
    }
}