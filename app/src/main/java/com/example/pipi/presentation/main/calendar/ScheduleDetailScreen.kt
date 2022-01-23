package com.example.pipi.presentation.main.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar

@Composable
fun ScheduleDetailScreen(date: String, navController: NavController, viewModel: CalendarViewModel) {
    Column() {
        DefaultTopAppbar(navComponent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = "뒤로가기",
                Modifier.clickable { navController.navigateUp() }
            )
        }, title = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date,
//                    text = calendar.currentDateTime.value,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }, optionComponent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = "일정추가",
                modifier = Modifier.clickable {
                    /**
                     * TODO : 파라미터로 받아온 navigation으로 운동 추가 화면으로 이동할것.
                     */
//                    navController.navigate()
                }
            )
        })
    }
}