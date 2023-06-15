package com.capstone.mymentor.ui.profile.mentor.posts

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.capstone.mymentor.DummyFeeds
import com.capstone.mymentor.DummyMentorPosts
import com.capstone.mymentor.R
import com.capstone.mymentor.databinding.ActivityDetailMentorPostsBinding
import com.capstone.mymentor.ui.feeds.DetailFeedsActivity

class DetailMentorPostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMentorPostsBinding

    companion object {
        const val EXTRA_TITLE = "extra_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMentorPostsBinding.inflate(layoutInflater)
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
        supportActionBar?.hide()
        val detail = intent.getParcelableExtra<DummyMentorPosts>(EXTRA_TITLE)
        binding.apply {
            tvDetailTopic.text = detail?.topic
            tvDetailCaption.text = detail?.title
        }
    }
}
