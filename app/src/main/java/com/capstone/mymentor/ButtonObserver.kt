package com.capstone.mymentor

import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView

class ButtonObserver(private val button: ImageView) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        if (charSequence.toString().trim().isNotEmpty()) {
            button.isEnabled = true
            button.setImageResource(R.drawable.baseline_send_24)
        } else {
            button.isEnabled = false
            button.setImageResource(R.drawable.baseline_send_gray)
        }
    }

    override fun afterTextChanged(p0: Editable?) {}
}