package com.capstone.mymentor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyChat(
    val name: String,
    val chat: String,
): Parcelable
