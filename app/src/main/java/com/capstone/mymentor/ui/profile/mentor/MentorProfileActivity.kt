package com.capstone.mymentor.ui.profile.mentor

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2
import com.capstone.mymentor.R
import com.capstone.mymentor.adapter.SectionsPagerAdapter
import com.capstone.mymentor.databinding.ActivityMentorProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MentorProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMentorProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)

        binding = ActivityMentorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(KEY_NAME)
        val position = intent.getStringExtra(KEY_POSITION)
        val rating = intent.getStringExtra(KEY_RATING)

        if (name != null && position != null && rating != null) {
            setMentorProfile(name, position, rating)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        setupView()
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

    private fun setMentorProfile(name: String, position: String, rating: String) {
        binding.tvMentorName.text = name
        binding.tvMentorPosition.text = position
        binding.tvRating.text = rating
    }

    companion object {
        const val KEY_NAME = "key_name"
        const val KEY_POSITION = "key_position"
        const val KEY_RATING = "key_rating"
        private val TAB_TITLES = intArrayOf(
            R.string.experiences_tab,
            R.string.posts_tab
        )
    }
}