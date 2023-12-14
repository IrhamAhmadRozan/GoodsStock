package com.example.goodstcok.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goodstcok.R

class OrderActivity : AppCompatActivity() {

//    private lateinit var imageUploadViewModel: ImageUploadViewModel

//    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val data: Intent? = result.data
//            val selectedImageUri: Uri? = data?.data
//            selectedImageUri?.let {
//                val imageFile = File(getRealPathFromURI(it))
//                imageUploadViewModel.uploadImage(imageFile)
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

//        imageUploadViewModel = ViewModelProvider(this).get(ImageUploadViewModel::class.java)
//
//        // Observe the upload status
//        imageUploadViewModel.uploadStatus.observe(this, Observer { isUploaded ->
//            if (isUploaded) {
//                // Handle success
//                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
//            } else {
//                // Handle failure
//                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        // Trigger image uploading
//        val imageFile = File("path_to_your_image")
//        imageUploadViewModel.uploadImage(imageFile)

    }
}