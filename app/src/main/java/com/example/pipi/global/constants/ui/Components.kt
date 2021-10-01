package com.example.pipi.global.constants.ui

import android.graphics.Color.BLACK
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.R
import com.example.pipi.global.constants.Fonts
import com.example.pipi.global.constants.ui.Colors.SURFACE

object Components {
    @Composable
    fun drawDefaultButton(color: Color, text: String, onClick: () -> Unit, isEnabled: Boolean) {
        Button(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .height(52.dp),
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = color,
            ),
            enabled = isEnabled
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

    @Composable
    fun drawTextTitleTopAppbar(title: String) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .padding(end = 76.dp)
                        .fillMaxWidth(),
                )
            },
            navigationIcon = { //navigationIcon이 뒤로가기, actions는 액션메뉴
                IconButton(
                    onClick = { Log.d("TAG", "goBackClicked!") },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "go Back",
                        )
                    },
                )
            },
            elevation = 0.dp, backgroundColor = SURFACE,
        )
    }

}