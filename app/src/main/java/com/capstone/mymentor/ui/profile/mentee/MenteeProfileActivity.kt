package com.capstone.mymentor.ui.profile.mentee

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.capstone.mymentor.R
import com.capstone.mymentor.databinding.ActivityMenteeProfileBinding
import com.capstone.mymentor.ui.onboarding.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth

class MenteeProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenteeProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)

        binding = ActivityMenteeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this@MenteeProfileActivity,"Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@MenteeProfileActivity, WelcomeActivity::class.java))
            finish()
        }

        setupView()
        alertDialogButton()
    }

    private fun alertDialogButton() {
        binding.btnEditProfile.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            with(alertDialog) {
                setTitle(getString(R.string.under_development))
                setMessage(getString(R.string.edit_profile_desc))
                setPositiveButton("Close", null)
                show()
            }
        }

        binding.btnAccount.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            with(alertDialog) {
                setTitle(getString(R.string.under_development))
                setMessage(getString(R.string.account_desc))
                setPositiveButton("Close", null)
                show()
            }
        }

        binding.btnManageNotification.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            with(alertDialog) {
                setTitle(getString(R.string.under_development))
                setMessage(getString(R.string.manage_notification_desc))
                setPositiveButton("Close", null)
                show()
            }
        }

        binding.btnSettings.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            with(alertDialog) {
                setTitle(getString(R.string.under_development))
                setMessage(getString(R.string.settings_desc))
                setPositiveButton("Close", null)
                show()
            }
        }

        binding.btnLikedPosts.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            with(alertDialog) {
                setTitle(getString(R.string.under_development))
                setMessage(getString(R.string.liked_posts_desc))
                setPositiveButton("Close", null)
                show()
            }
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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}