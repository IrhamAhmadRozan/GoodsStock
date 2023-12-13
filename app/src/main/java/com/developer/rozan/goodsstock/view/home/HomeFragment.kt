package com.developer.rozan.goodsstock.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.util.extention.show
import com.developer.rozan.goodsstock.util.extention.showBottomNav
import com.developer.rozan.goodsstock.view.dashboard.DashboardActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private val bottomNavigationView by lazy {
        (activity as DashboardActivity).findViewById<BottomNavigationView>(
            R.id.bottom_navigation_view
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as DashboardActivity).showBottomNav()
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }

    private fun showBottomNavigationView() {
        bottomNavigationView.animate().scaleY(1f)
        bottomNavigationView.show()
    }
}