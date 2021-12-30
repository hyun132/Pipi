package com.example.pipi.presentation.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Components


@Composable
fun UserInfoScreen() {
    Column() {
        DrawTopAppBar(text = "회원 정보", R.drawable.ic_back) {/* TODO : goback()*/ }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_onboarding), contentDescription = "프로필 이미지",
                Modifier
                    .size(119.dp)
                    .clip(shape = RoundedCornerShape(8.dp)))
            Text(text = "변상진 회원님",style = MaterialTheme.typography.h1,color = PRIMARY_TEXT)
        }
        
        Text(text = "인바디")
    }
}

@Composable
fun DrawTopAppBar(text: String, navIcon: Int, goBack: () -> Unit) {
    Components.DefaultTopAppbar(title = {
        Text(
            text = text,
            style = MaterialTheme.typography.h1,
            color = Colors.PRIMARY_TEXT,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }, navComponent = {
        Icon(
            imageVector = ImageVector.vectorResource(id = navIcon),
            contentDescription = "닫기",
            Modifier
                .size(12.dp)
                .clickable { goBack() }
        )
    })
}
