package com.example.goodstcok.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.goodstcok.R
import com.example.goodstcok.data.local.sharedpref.UserPref

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var layoutBack : ConstraintLayout
    private lateinit var titleToolbarBack : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        UserPref.init = UserPref(this)
        UserPref.init.getToken()

        layoutBack = findViewById(R.id.layout_back)
        titleToolbarBack = findViewById(R.id.title_toolbar_back)

        titleToolbarBack.text = "Add Category"
        layoutBack.setOnClickListener {
            finish()
        }
    }
}