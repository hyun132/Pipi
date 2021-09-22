package com.example.pipi.global.constants.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.global.constants.Fonts

object Components {
    @Composable
    fun drawDefaultButton(color: Color, text: String, onClick: () -> Unit) {
        Button(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .height(52.dp),
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = color,
            )
        ) {
            Text(
                fontSize = 16.sp,
                text = text,
                color = Color.White,
                fontFamily = Fonts.appFontFamily,
                fontWeight = FontWeight.W700
            )
        }
    }
}