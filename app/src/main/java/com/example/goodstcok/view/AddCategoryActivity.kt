package com.example.goodstcok.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.goodstcok.R
import com.example.goodstcok.data.api.RemoteService
import com.example.goodstcok.data.api.entity.BaseResponse
import com.example.goodstcok.data.local.sharedpref.UserPref
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONException

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var layoutBack: ConstraintLayout
    private lateinit var titleToolbarBack: TextView
    private lateinit var tfCategoryName: TextInputLayout
    private lateinit var btnSubmit: Button

    private val remoteService = RemoteService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        UserPref.init = UserPref(this)

        layoutBack = findViewById(R.id.layout_back)
        titleToolbarBack = findViewById(R.id.title_toolbar_back)
        tfCategoryName = findViewById(R.id.tf_category_name)
        btnSubmit = findViewById(R.id.btn_submit)

        titleToolbarBack.text = "Add Category"
        layoutBack.setOnClickListener {
            finish()
        }

        btnSubmit.setOnClickListener {
            val categoryName = tfCategoryName.editText?.text.toString()

            if (categoryName.isNotEmpty()) {
                addProductAction(categoryName)
            } else {
                Toast.makeText(this, "All textfield must be field!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun addProductAction(categoryName: String) {
        val token = UserPref.init.getToken()

        remoteService.uploadCategory(
            "Token $token",
            categoryName,
            object : BaseResponse<String> {
                override fun onSuccess(response: String) {
                    try {
                        uploadBerhasil(response)
                    } catch (e: JSONException) {
                        uploadGagal(e)
                    }
                }

                override fun onError(error: String) {
                    uploadGagal(error)
                }

            })
    }

    private fun uploadBerhasil(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun uploadGagal(e: JSONException) {
        Toast.makeText(this, "Error : " + e.message, Toast.LENGTH_SHORT).show()
    }

    private fun uploadGagal(msg: String) {
        Toast.makeText(this, "Error : $msg", Toast.LENGTH_SHORT).show()
    }
}