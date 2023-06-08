package com.capstone.mymentor.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Personality(@StringRes val stringResourceId: Int, @DrawableRes val imageResourceId: Int)

data class Roles(@StringRes val stringResourceId: Int, @DrawableRes val imageResourceId: Int)

data class MenteePosition(@StringRes val stringResourceId: Int)

data class MentorPosition(@StringRes val stringResourceId: Int)

data class YearsPosition(@StringRes val stringResourceId: Int)