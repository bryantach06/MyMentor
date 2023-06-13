package com.capstone.mymentor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyFeeds(
    val name: String,
    val caption: String,
//    val photo: Int,
): Parcelable