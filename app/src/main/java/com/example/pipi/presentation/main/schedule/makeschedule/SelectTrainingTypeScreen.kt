package com.example.pipi.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.QUATERNARY_BRAND
import com.example.pipi.global.constants.ui.Colors.TERTIARY_BRAND
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.utils.CalendarUtils.getCurrentDateString
import java.util.*

@Composable
fun SelectTrainingTypeScreen(navigate: () -> Unit, goBack: () -> Unit, calendar: Calendar) {

    Scaffold(topBar = { DrawTopAppbar(goBack = goBack, calendar = calendar) }) {
        Column(Modifier.fillMaxSize()) {
            DrawTrainingTypeItem(
                title = "PT 퍼스널 트레이닝",
                description = "선생님과 수행한 운동을 기록합니다.",
                QUATERNARY_BRAND,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navigate()
                    }
            )
            DrawTrainingTypeItem(
                title = "AT 과제 트레이닝",
                description = "학생에게 일 단위 운동 루틴을 제공합니다.",
                TERTIARY_BRAND,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navigate()
                    }
            )
        }
    }
}

@Composable
fun DrawTopAppbar(goBack: () -> Unit, calendar: Calendar) {
    DefaultTopAppbar(navComponent = {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
            contentDescription = "뒤로가기",
            modifier = Modifier.clickable { goBack() }
        )
    }, title = {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = calendar.getCurrentDateString(),
                style = MaterialTheme.typography.subtitle2
            )
        }
    })
}

@Composable
fun DrawTrainingTypeItem(
    title: String,
    description: String,
    titleColor: Color,
    modifier: Modifier
) {
    Card(modifier = modifier) {
        Text(text = title, color = titleColor)
        Text(text = description)
    }
}