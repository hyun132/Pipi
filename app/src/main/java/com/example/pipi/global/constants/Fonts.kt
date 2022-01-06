package com.example.pipi.global.constants

import androidx.compose.material.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.R

object Fonts {
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

    val typography = Typography(
        defaultFontFamily = appFontFamily,
        h1 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 96.sp,
            letterSpacing = (-1.5).sp,
            lineHeight = 28.sp,
        ),
        h2 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 60.sp,
            letterSpacing = (-0.5).sp,
            lineHeight = 28.sp,
        ),
        h3 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            letterSpacing = 0.sp
        ),
        h4 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 34.sp,
            letterSpacing = 0.25.sp
        ),
        h5 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp
        ),
        h6 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.15.sp
        ),
        //size:16 , weight:400 , lineHight:24
        subtitle1 = TextStyle(
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp,
            fontSize = 16.sp,
            letterSpacing = 0.15.sp
        ),
        // p2
        subtitle2 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
        body1 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        ),
        body2 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.25.sp
        ),
        button = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 1.25.sp
        ),

        )
}