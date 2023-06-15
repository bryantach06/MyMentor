package com.capstone.mymentor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyMentorPosts(
    val title: String,
    val topic: String,
//    val image: Int,
): Parcelable
