package com.example.pipi.global.constants.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.pipi.R

sealed class MenuAction(
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    object Back:MenuAction(R.string.appbar_back,R.drawable.ic_back)
}
