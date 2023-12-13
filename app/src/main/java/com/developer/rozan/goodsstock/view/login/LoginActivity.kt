package com.developer.rozan.goodsstock.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.view.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}