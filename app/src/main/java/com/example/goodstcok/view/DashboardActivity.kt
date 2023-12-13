package com.example.goodstcok.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.*
import androidx.navigation.ui.setupWithNavController
import com.example.goodstcok.R
import com.example.goodstcok.utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }
    private fun initBottomNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navController.addOnDestinationChangedListener(this)
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.navigation_home, R.id.navigation_profile -> showBottomNav()

            else -> hideBottomNav()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }
}