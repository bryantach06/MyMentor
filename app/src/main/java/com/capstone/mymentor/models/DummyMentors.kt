package com.capstone.mymentor.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyMentors(
    val name: String,
    val position: String,
    val workplace: String
): Parcelable

@Parcelize
data class DummyExperiences(
    val position: String,
    val place: String,
    val startDate: String,
    val endDate: String,
    val logo: String
) : Parcelable