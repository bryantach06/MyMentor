package com.capstone.mymentor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.capstone.mymentor.ui.onboarding.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val uid = auth.currentUser?.uid

        uid?.let { userId ->
            val userRef = firestore.collection("users").document(userId)
            userRef.get().addOnSuccessListener { userSnapshot ->
                if (userSnapshot.exists()) {
                    val token = userSnapshot.getString("Token")
                    if (!token.isNullOrEmpty()) {
                        // Session data is available, user is still logged in
                        // Proceed to MainActivity
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }, 3000)
                    } else {
                        // Session data is not available, redirect to LoginActivity
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this, WelcomeActivity::class.java))
                            finish()
                        }, 3000)
                    }
                } else {
                    // User document does not exist, redirect to LoginActivity
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(this, WelcomeActivity::class.java))
                        finish()
                    }, 3000)
                }
            }.addOnFailureListener { exception ->
                // Handle the failure
                val errorMessage = exception.message
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            // User is not authenticated, redirect to LoginActivity
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }, 3000)
        }
    }
}
