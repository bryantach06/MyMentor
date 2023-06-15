package com.capstone.mymentor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.mymentor.databinding.ActivityMainBinding
import com.capstone.mymentor.ui.login.LoginActivity
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
                        val token = userSnapshot.getString("token")
                        if (token != null) {
                            // Session data is available, user is still logged in
                            // Proceed with the normal flow
                            setupView()
                            //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.navigationBarColor = ContextCompat.getColor(this, R.color.main_purple)
//        }


//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

                            val navView: BottomNavigationView = binding.navView
                            navView.itemIconTintList = null
                            navView.itemTextColor = getColorStateList(R.color.main_pink)

                            val navController =
                                findNavController(R.id.nav_host_fragment_activity_main)
                            // Passing each menu ID as a set of Ids because each
                            // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home,
//                R.id.navigation_feeds,
//                R.id.navigation_chat,
//                R.id.navigation_events
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
                            navView.setupWithNavController(navController)
                        } else {
                            // Session data is not available, redirect to LoginActivity
                            startActivity(Intent(this, WelcomeActivity::class.java))
                            finish()
                        }
                    } else {
                        // User document does not exist, redirect to LoginActivity
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
