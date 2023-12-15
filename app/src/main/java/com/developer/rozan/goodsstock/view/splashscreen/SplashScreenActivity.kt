package com.developer.rozan.goodsstock.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.data.local.sharedpref.UserPref
import com.developer.rozan.goodsstock.view.dashboard.DashboardActivity
import com.developer.rozan.goodsstock.view.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        UserPref.init = UserPref(this)

        image = findViewById(R.id.image)

        image.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
        lifecycleScope.launch {
            delay(1500L)
            toNextActivity()
            finish()
        }
    }

    private fun toNextActivity() {
        if (UserPref.init.isLogin()) {
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}