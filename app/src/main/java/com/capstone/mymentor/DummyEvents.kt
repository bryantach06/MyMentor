package com.capstone.mymentor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyEvents(
    val title: String,
    val location: String,
    val time: String,
    val price: String,
    val date: String,
    val speaker: String,
): Parcelable