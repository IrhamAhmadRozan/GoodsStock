package com.developer.rozan.goodsstock.view.add_product

import android.Manifest
import android.R
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.developer.rozan.goodsstock.data.api.RemoteService
import com.developer.rozan.goodsstock.data.api.entity.BaseResponse
import com.developer.rozan.goodsstock.data.local.entity.Category
import com.developer.rozan.goodsstock.data.local.sharedpref.UserPref
import com.developer.rozan.goodsstock.util.extension.noSpaceDate
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONException

class AddProductActivity : AppCompatActivity() {

    private lateinit var layoutBack: ConstraintLayout
    private lateinit var titleToolbarBack: TextView
    private lateinit var rlTakePhoto: RelativeLayout
    private lateinit var ivPhotoResult: ImageView
    private lateinit var tfProductName: TextInputLayout
    private lateinit var spinnerCategory: AutoCompleteTextView
    private lateinit var tfProductPrice: TextInputLayout
    private lateinit var tfProductQuantity: TextInputLayout
    private lateinit var tfDescription: TextInputLayout
    private lateinit var btnSubmit: Button

    private var listNameCategory: MutableList<String> = ArrayList()
    private var getCategoryId: Int = 0
    private var categories: List<Category> = mutableListOf()

    private val remoteService = RemoteService()
    private val REQUEST_TAKE_PHOTO = 132

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.developer.rozan.goodsstock.R.layout.activity_add_product)

        UserPref.init = UserPref(this)

        layoutBack = findViewById(com.developer.rozan.goodsstock.R.id.layout_back)
        titleToolbarBack = findViewById(com.developer.rozan.goodsstock.R.id.title_toolbar_back)
        rlTakePhoto = findViewById(com.developer.rozan.goodsstock.R.id.rl_take_photo)
        ivPhotoResult = findViewById(com.developer.rozan.goodsstock.R.id.iv_photo_result)
        tfProductName = findViewById(com.developer.rozan.goodsstock.R.id.tf_product_name)
        spinnerCategory = findViewById(com.developer.rozan.goodsstock.R.id.spinner_category)
        tfProductPrice = findViewById(com.developer.rozan.goodsstock.R.id.tf_product_price)
        tfProductQuantity = findViewById(com.developer.rozan.goodsstock.R.id.tf_product_stock)
        tfDescription = findViewById(com.developer.rozan.goodsstock.R.id.tf_product_description)
        btnSubmit = findViewById(com.developer.rozan.goodsstock.R.id.btn_submit)

        titleToolbarBack.text = "Add Product"
        layoutBack.setOnClickListener {
            finish()
        }

        rlTakePhoto.setOnClickListener {
            takePhotoAction()
        }

        btnSubmit.setOnClickListener {
            val productName = tfProductName.editText?.text.toString()
            val productPrice = tfProductPrice.editText?.text.toString()
            val productQuantity = tfProductQuantity.editText?.text.toString()
            val productDescription = tfDescription.editText?.text.toString()


            if ((productName.isNotEmpty()) and (getCategoryId != 0) and (productPrice.isNotEmpty()) and (productDescription.isNotEmpty()) and (productQuantity.isNotEmpty())) {
                addProductAction(
                    productName,
                    getCategoryId,
                    productPrice.toInt(),
                    productDescription,
                    productQuantity.toInt()
                )
            } else {
                Toast.makeText(this, "All textfield must be field!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        setUpCategory()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                if (data != null) {
                    ivPhotoResult.setImageBitmap(data.extras?.get("data") as Bitmap)
                } else {
                    Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun takePhotoAction() {
        val fileName = "Product-".noSpaceDate().toString()
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager).also {
                val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
                } else {
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }

        }
    }

    private fun addProductAction(
        productName: String,
        productCategory: Int,
        productPrice: Int,
        productDescrption: String,
        productQuantity: Int
    ) {
        val token = UserPref.init.getToken()

        remoteService.uploadProduct(
            "Token $token",
            productName,
            productCategory,
            productPrice,
            productDescrption,
            productQuantity,
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
        categories = category
        for (i in category.indices) {
            listNameCategory.add(category[i].name)
            val spinnerArrayAdapter =
                ArrayAdapter(this, R.layout.simple_spinner_item, listNameCategory)
            spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            spinnerCategory.setAdapter(spinnerArrayAdapter)
        }

        spinnerCategory.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                for (i in categories.indices) {
                    if (selectedItem == categories[i].name) {
                        getCategoryId = categories[i].id
                    }
                }
            }
        }
    }

    private fun getDataCategoryGagal(e: String) {
        Toast.makeText(this, "Error : $e", Toast.LENGTH_SHORT).show()
    }
}