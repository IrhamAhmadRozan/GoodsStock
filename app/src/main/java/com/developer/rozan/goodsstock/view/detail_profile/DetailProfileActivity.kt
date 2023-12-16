package com.developer.rozan.goodsstock.view.detail_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.util.extension.visible
import com.developer.rozan.goodsstock.view.add_product.AddProductActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DetailProfileActivity : AppCompatActivity() {

    private lateinit var layoutBack : ConstraintLayout
    private lateinit var titleToolbarBack : TextView
    private lateinit var layoutActionBarBack : LinearLayout
    private lateinit var layoutAction2Back : ConstraintLayout
    private lateinit var tfName : TextInputEditText
    private lateinit var tfEmail : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_profile)

        var name = intent.getStringExtra("name").toString()
        var email = intent.getStringExtra("email").toString()

        layoutBack = findViewById(R.id.layout_back)
        titleToolbarBack = findViewById(R.id.title_toolbar_back)
        layoutActionBarBack = findViewById(R.id.layout_action_bar_back)
        layoutAction2Back = findViewById(R.id.layout_action2_back)
        tfName = findViewById(R.id.tf_name)
        tfEmail = findViewById(R.id.tf_email)

        titleToolbarBack.text = "Edit Profile"
        layoutActionBarBack.visible()
        layoutAction2Back.visible()
        layoutBack.setOnClickListener {
            finish()
        }

        layoutAction2Back.setOnClickListener {
            Toast.makeText(this, "Feature is under development.", Toast.LENGTH_SHORT).show()
        }

        tfName.setText(name)
        tfEmail.setText(email)
    }
}