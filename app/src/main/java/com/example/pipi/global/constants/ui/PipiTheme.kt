package com.example.pipi.global.constants.ui


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.pipi.global.constants.Fonts.typography
import com.example.pipi.global.constants.ui.Colors.MAIN_PURPLE
import com.example.pipi.global.constants.ui.Colors.SURFACE

private val DarkColor = darkColors(
    primary = MAIN_PURPLE,
    primaryVariant = Color(0xffffffff),
    secondary = Color(0xff5d00d4),
    secondaryVariant = Color(0xff55D95A),
    surface = SURFACE
)
private val LightColor = lightColors(
    primary = MAIN_PURPLE,
    primaryVariant = Color(0xff000000),
    secondary = Color(0xff5d00d4),
    secondaryVariant = Color(0xff55D95A),
    surface = SURFACE
)

@Composable
fun setProjectTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) DarkColor else LightColor,
        content = content,
        typography = typography
    )
//https://medium.com/mobile-app-development-publication/android-jetpack-compose-theme-made-easy-1812150239fe

}