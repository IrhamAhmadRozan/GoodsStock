package com.developer.rozan.goodsstock.view.add_category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.developer.rozan.goodsstock.R

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var layoutBack : ConstraintLayout
    private lateinit var titleToolbarBack : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        layoutBack = findViewById(R.id.layout_back)
        titleToolbarBack = findViewById(R.id.title_toolbar_back)

        titleToolbarBack.text = "Add Category"
        layoutBack.setOnClickListener {
            finish()
        }
    }
}