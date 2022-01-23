package com.example.pipi.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.QUATERNARY_BRAND
import com.example.pipi.global.constants.ui.Colors.TERTIARY_BRAND
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.utils.CalendarUtils
import java.util.*

@Composable
fun SelectTrainingTypeScreen(navController: NavController, calendar: Calendar) {
    Column(Modifier.fillMaxWidth()) {
        DefaultTopAppbar(navComponent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = "뒤로가기"
            )
        }, title = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    contentDescription = "뒤로가기"
                )
                Text(
                    text = "년  일",
                    style = MaterialTheme.typography.subtitle2
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    contentDescription = "뒤로가기"
                )
            }
        })
    }

    Column(Modifier.fillMaxSize()) {
        DrawTrainingTypeItem(
            title = "PT 퍼스널 트레이닝",
            description = "선생님과 수행한 운동을 기록합니다.",
            QUATERNARY_BRAND
        )
        DrawTrainingTypeItem(
            title = "AT 과제 트레이닝",
            description = "학생에게 일 단위 운동 루틴을 제공합니다.",
            TERTIARY_BRAND
        )
    }
}

@Composable
fun DrawTrainingTypeItem(title: String, description: String, titleColor: Color) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, color = titleColor)
        Text(text = description)
    }
}