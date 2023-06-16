package com.capstone.mymentor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.mymentor.databinding.ActivityMainBinding
import com.capstone.mymentor.ui.onboarding.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val uid = auth.currentUser?.uid
        uid?.let { userId ->
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { userSnapshot ->
                    if (userSnapshot.exists()) {
                        val token = userSnapshot.getString("Token")
                        if (token != null) {
                            // Session data is available, user is still logged in
                            // Proceed with the normal flow
                            setupView()

                            val navView: BottomNavigationView = binding.navView
                            navView.itemIconTintList = null
                            navView.itemTextColor = getColorStateList(R.color.main_pink)

                            val navController =
                                findNavController(R.id.nav_host_fragment_activity_main)
                            navView.setupWithNavController(navController)
                        } else {
                            // Session data is not available, redirect to LoginActivity
                            Log.d("Main Activity", "Session Data Unavailable")
                            startActivity(Intent(this, WelcomeActivity::class.java))
                            finish()
                        }
                    } else {
                        // User document does not exist, redirect to LoginActivity
                        Log.d("Main Activity", "User Doc doesn't Exist")
                        startActivity(Intent(this, WelcomeActivity::class.java))
                        finish()
                    }
                }.addOnFailureListener { exception ->
                // Handle the failure
                    val errorMessage = exception.message
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            // User is not authenticated, redirect to LoginActivity
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
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
    }
}
