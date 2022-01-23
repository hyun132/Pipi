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
        //t1
        h1 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = (-1.5).sp,
            lineHeight = 28.sp,
        ),
        //h1
        h2 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeight = 28.sp,
        ),
        //h2
        h3 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeight = 28.sp,
        ),
        //p1
        h4 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            letterSpacing = 0.25.sp,
            lineHeight = 24.sp,
        ),
        //p2
        h5 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            letterSpacing = 0.25.sp,
            lineHeight = 24.sp,
        ),
        //p3
        h6 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp,
            lineHeight = 24.sp,
        ),
        //p4
        subtitle1 = TextStyle(
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp,
            fontSize = 14.sp,
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
        //p4
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