package com.capstone.mymentor.ui.events

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.capstone.mymentor.DummyEvents
import com.capstone.mymentor.R
import com.capstone.mymentor.databinding.ActivityDetailEventsBinding

class DetailEventsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
    }

    private lateinit var binding: ActivityDetailEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBook.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Feature under development!")
                setMessage("Event Booking feature will be available upon future development!")
                setPositiveButton("Close", null)
                create()
                show()
            }
        }

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
        val detail = intent.getParcelableExtra<DummyEvents>(EXTRA_TITLE)
        binding.apply {
            tvEventTitle.text = detail?.title
            tvEventDetailsLocation.text = detail?.location
            tvEventDetailDate.text = "${detail?.date} / "
            tvEventDetailsTime.text = "${detail?.time}"
            tvEventDetailsDescription.text = getString(R.string.lorem_ipsum)
            tvEventDetailsPrice.text = detail?.price
            tvEventDetailsSpeaker.text = detail?.speaker
        }
    }
}
