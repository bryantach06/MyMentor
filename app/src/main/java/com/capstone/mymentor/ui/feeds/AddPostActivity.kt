package com.capstone.mymentor.ui.feeds

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.capstone.mymentor.MainActivity
import com.capstone.mymentor.createCustomTempFile
import com.capstone.mymentor.databinding.ActivityAddPostBinding
import com.capstone.mymentor.rotateFile
import com.capstone.mymentor.uriToFile
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        setupView()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnCamera.setOnClickListener { startTakePhoto() }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.tvPost.setOnClickListener { uploadImage() }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddPostActivity,
                "com.capstone.mymentor",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun uploadImage() {
        val user = Firebase.auth.currentUser
        val email = user?.email.toString()
        val firestore = FirebaseFirestore.getInstance()
        val collectionPath = "Users Posts"
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val filename = "image_${System.currentTimeMillis()}.jpg" // Generate a unique filename
        val imageRef = storageRef.child("images/$filename") // Set the desired storage path
        val file =
            File(currentPhotoPath) // Assuming `currentPhotoPath` is the path of the selected image
        val imageUri = Uri.fromFile(file)
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result.toString() // The download URL of the uploaded image
                val title = binding.edAddPostTitle.text.toString()
                val description = binding.edAddPostCaption.text.toString()
                val user = Firebase.auth.currentUser?.email.toString()

                val postData = hashMapOf(
                    "Image" to downloadUrl,
                    "Title" to title,
                    "Description" to description,
                    "User" to user,
                )

                firestore.collection(collectionPath)
                    .document(email)
                    .set(postData)
                    .addOnSuccessListener {
                        AlertDialog.Builder(this@AddPostActivity).apply {
                            setTitle("Feature Under Development!")
                            setMessage("Post Story feature will be available upon further development!\nYour post data is currently stored, but is yet to be displayed. We appreciate your patience to wait for this feature development!")
                            setPositiveButton("Back to Home") { _, _ ->
                                val intent = Intent(this@AddPostActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Failed to upload post: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e(
                            "UploadStoryResponse",
                            "Error: ${e.message}"
                        )
                    }
            } else {
                Log.d("Upload image to storage", "Error")
            }
        }
    }

    private lateinit var currentPhotoPath: String

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
//              Silakan gunakan kode ini jika mengalami perubahan rotasi
                rotateFile(file)
                binding.ivAddStory.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                binding.ivAddStory.setImageURI(uri)
            }
        }
    }

}