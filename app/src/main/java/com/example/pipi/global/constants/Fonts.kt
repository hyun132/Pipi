package com.example.pipi.global.constants

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.pipi.R

object Fonts{
    val appFontFamily = FontFamily(
        fonts = listOf(
            Font(
                resId = R.font.notosans_regular,
                style = FontStyle.Normal,
                weight = FontWeight.Normal
            ),
            Font(
                resId = R.font.notosans_bold,
                style = FontStyle.Normal,
                weight = FontWeight.Bold
            ),
            Font(
                resId = R.font.notosans_italic,
                style = FontStyle.Italic,
                weight = FontWeight.Normal
            )
        )

    )
}