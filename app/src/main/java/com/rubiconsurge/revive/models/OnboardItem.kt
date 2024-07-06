package com.rubiconsurge.revive.models

import androidx.annotation.DrawableRes

data class OnboardItem(
    @DrawableRes val image: Int,
    val text: String,
)
