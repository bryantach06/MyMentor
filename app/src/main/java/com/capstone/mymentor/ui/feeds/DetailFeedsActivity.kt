package com.capstone.mymentor.ui.feeds

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.capstone.mymentor.DummyFeeds
import com.capstone.mymentor.databinding.ActivityDetailFeedsBinding

class DetailFeedsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    private lateinit var binding: ActivityDetailFeedsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFeedsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        setupView()
    }

    @SuppressLint("SetTextI18n")
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
        val bottomNavigationBarHeight = getNavigationBarHeight(this)
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setPadding(0, 0, 0, bottomNavigationBarHeight)
        supportActionBar?.hide()
        val detail = intent.getParcelableExtra<DummyFeeds>(EXTRA_NAME)
        binding.apply {
            tvDetailName.text = detail?.name
            tvDetailCaption.text = detail?.caption
        }
    }

    private fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

}
