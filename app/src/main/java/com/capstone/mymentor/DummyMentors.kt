package com.capstone.mymentor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyMentors(
    val name: String,
    val position: String,
    val workplace: String
): Parcelable