package com.developer.rozan.goodsstock.view.add_product

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.developer.rozan.goodsstock.data.api.RemoteService
import com.developer.rozan.goodsstock.data.api.entity.BaseResponse
import com.developer.rozan.goodsstock.data.local.entity.Category
import com.developer.rozan.goodsstock.data.local.sharedpref.UserPref
import com.developer.rozan.goodsstock.util.extension.visible
import com.google.android.material.textfield.MaterialAutoCompleteTextView


class AddProductActivity : AppCompatActivity() {

    private lateinit var layoutBack : ConstraintLayout
    private lateinit var titleToolbarBack : TextView
    private lateinit var spinnerCategory : MaterialAutoCompleteTextView

    var listNameCategory : MutableList<String> = ArrayList()

    private val remoteService = RemoteService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.developer.rozan.goodsstock.R.layout.activity_add_product)

        UserPref.init = UserPref(this)

        layoutBack = findViewById(com.developer.rozan.goodsstock.R.id.layout_back)
        titleToolbarBack = findViewById(com.developer.rozan.goodsstock.R.id.title_toolbar_back)
        spinnerCategory = findViewById(com.developer.rozan.goodsstock.R.id.spinner_category)

        titleToolbarBack.text = "Add Product"
        layoutBack.setOnClickListener {
            finish()
        }

        setUpCategory()
    }

    private fun setUpCategory() {
        val token = UserPref.init.getToken()

        remoteService.getCategoryList("Token $token", object : BaseResponse<List<Category>> {
            override fun onSuccess(response: List<Category>) {
                initSpinner(response)
            }

            override fun onError(error: String) {
                getDataCategoryGagal(error)
            }
        })
    }

    private fun initSpinner(category: List<Category>) {
        for (i in category.indices) {
            listNameCategory.add(category[i].name)
            val spinnerArrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, listNameCategory)
            spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            spinnerCategory.setAdapter(spinnerArrayAdapter)
        }

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, "You have selected $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")

            }
        }
    }

    private fun getDataCategoryGagal(e: String) {
        Toast.makeText(this, "Error : $e", Toast.LENGTH_SHORT).show()
    }
}