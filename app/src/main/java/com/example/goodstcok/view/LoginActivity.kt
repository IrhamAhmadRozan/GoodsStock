package com.example.goodstcok.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.goodstcok.R
import com.example.goodstcok.data.api.RemoteService
import com.example.goodstcok.data.api.entity.BaseResponse

import com.example.goodstcok.data.local.sharedpref.UserPref
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONException

class LoginActivity : AppCompatActivity() {

    private lateinit var tfUsername : TextInputLayout
    private lateinit var tfPassword : TextInputLayout
    private lateinit var btnLogin : Button
    private lateinit var tvLupaKataSandi : TextView

    private val remoteService = RemoteService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        UserPref.init = UserPref(this)

        tfUsername = findViewById(R.id.tf_username)
        tfPassword = findViewById(R.id.tf_password)
        btnLogin = findViewById(R.id.btn_login)
        tvLupaKataSandi = findViewById(R.id.tv_lupa_kata_sandi)

        btnLogin.setOnClickListener {
            val username = tfUsername.editText?.text.toString()
            val password = tfPassword.editText?.text.toString()

            if ((username.isNotEmpty()) and (password.isNotEmpty())) {
                loginAction(username, password)
            } else {
                Toast.makeText(this, "Username and Password must be field!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        tvLupaKataSandi.setOnClickListener {
            Toast.makeText(this, "Fitur sedang dalam pengembangan.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginAction(username: String, password: String) {
        remoteService.loginUser(username, password, object : BaseResponse<String> {
            override fun onSuccess(response: String) {
                try {
                   loginBerhasil(response)
                } catch (e: JSONException) {
                    loginGagal(e)
                }
            }

            override fun onError(error: String) {
                loginGagal(error)
            }

        })
    }

    private fun loginBerhasil(token: String) {
        Toast.makeText(this, "Berhasil Login!", Toast.LENGTH_SHORT).show()
        UserPref.init.loggedIn()
        UserPref.init.saveToken(token)
        startActivity(Intent(this, DashboardActivity::class.java))
    }

    private fun loginGagal(e: JSONException) {
        Toast.makeText(this, "Error : " + e.message, Toast.LENGTH_SHORT).show()
    }

    private fun loginGagal(e: String) {
        Toast.makeText(this, "Error : " + e, Toast.LENGTH_SHORT).show()
    }
}