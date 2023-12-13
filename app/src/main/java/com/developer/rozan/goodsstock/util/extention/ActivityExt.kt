package com.developer.rozan.goodsstock.util.extention

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.content.ContextCompat.getSystemService
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.view.dashboard.DashboardActivity

fun Activity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(false)
    } else {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}

fun DashboardActivity.showBottomNav(){
    val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
    if(!navigation.isShown)
        navigation.show()
}

fun DashboardActivity.hideBottomNav(){
    val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
    navigation.hide()
}

fun Activity.hideKeyboard(editText: EditText){
    editText.clearFocus()
    val inputMethodManager: InputMethodManager? =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.hideSoftInputFromWindow(editText.windowToken, 0)
}