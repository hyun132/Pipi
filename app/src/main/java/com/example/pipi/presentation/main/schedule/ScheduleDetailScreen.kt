package com.example.pipi.presentation.main.schedule

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.utils.CalendarUtils.getCurrentDateString
import java.util.*

@Composable
fun ScheduleDetailScreen(calendar: Calendar, goBack: () -> Unit, navigate: () -> Unit) {
    Column() {
        DefaultTopAppbar(navComponent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = "뒤로가기",
                Modifier.clickable { goBack() }
            )
        }, title = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = calendar.getCurrentDateString(),
                    style = MaterialTheme.typography.h5
                )
            }
        }, optionComponent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                contentDescription = "일정추가",
                modifier = Modifier.clickable {
                    /**
                     * TODO : 파라미터로 받아온 navigation으로 운동 추가 화면으로 이동할것.
                     */
                    navigate()
                }
            )
        })
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(
                text = "트레이닝 일정이 없네요\n일정을 추가해보세요!",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .align(CenterVertically)
            )
        }
    }
}