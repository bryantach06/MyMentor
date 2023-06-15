package com.capstone.mymentor.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.capstone.mymentor.R
import com.capstone.mymentor.databinding.ActivityRegisterBinding
import com.capstone.mymentor.ui.login.LoginActivity
import com.capstone.mymentor.ui.questions.QuestionsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        binding.googleLogin.setOnClickListener {
            signIn()
        }

        binding.tvSignupActionLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        setupView()
        setupAction()
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

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this@RegisterActivity, QuestionsActivity::class.java))
//            finish()
        }
    }

    private fun setMyButtonEnable() {
        val name = binding.name.text
        val email = binding.email.text
        val password = binding.password.text
        val confirmPassword = binding.confirmPassword.text
        binding.signup.isEnabled =
            name != null && email != null && password != null && confirmPassword != null && name.toString()
                .isNotEmpty() && email.toString()
                .isNotEmpty() && password.toString()
                .isNotEmpty() && confirmPassword.toString().isNotEmpty()
    }

    private fun setupAction() {
        val password = binding.password
        val confirmPassword = binding.confirmPassword

        binding.name.addTextChangedListener {
            setMyButtonEnable()
        }

        binding.email.addTextChangedListener {
            setMyButtonEnable()
        }

        password.addTextChangedListener {
            val password = binding.password.text
            if (password?.length!! >= 8) {
                setMyButtonEnable()
            } else {
                binding.signup.isEnabled = false
            }
        }

        confirmPassword.addTextChangedListener {
            val confirmPassword = binding.confirmPassword.text
            if (confirmPassword?.length!! >= 8) {
                setMyButtonEnable()
            } else {
                binding.signup.isEnabled = false
            }
        }

        binding.signup.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()
            val name = binding.name.text.toString()
            when {
                password != confirmPassword -> {
                    binding.confirmPassword.error = "Password do not match!"
                }

                else -> {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Account created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val user = auth.currentUser
                                user?.let {
                                    saveUserNameToFirestore(user.uid, name)
                                }
                                updateUI(user)
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Signup failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                                updateUI(null)
                            }
                        }
                }
            }
        }
    }

    private fun saveUserNameToFirestore(userId: String?, name: String) {
        userId?.let {
            db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(userId)

            val userData = hashMapOf(
                "Name" to name,
                "Token" to userId // Example: Use the UID as the session token
            )

            userRef.set(userData)
                .addOnSuccessListener {
                    // Successful save
                }
                .addOnFailureListener { e ->
                    // Handle error
                    Toast.makeText(
                        this@RegisterActivity,
                        "Failed to save session data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }

}