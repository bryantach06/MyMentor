package com.capstone.mymentor.ui.mentorprofile

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.capstone.mymentor.models.DummyMentors
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

        @Suppress("DEPRECATION")
        val dummyMentors = intent.getParcelableExtra<DummyMentors>("key_mentors") as DummyMentors
        if (dummyMentors != null) {
            binding.tvMentorName.text = dummyMentors.name
            binding.tvMentorPosition.text = buildString {
                append(dummyMentors.position)
                append(",")
            }
            binding.tvMentorWorkplace.text = dummyMentors.workplace
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        setupView()
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
//        supportActionBar?.title = null
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
//        supportActionBar?.hide()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.experiences_tab,
            R.string.posts_tab
        )
    }
}