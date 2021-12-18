package com.example.pipi.presentation.main.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pipi.R
import com.example.pipi.global.constants.utils.BaseCalendar
import timber.log.Timber

@ExperimentalFoundationApi
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    onCalendarItemClicked: (String) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        DrawBaseCalendar(viewModel, onCalendarItemClicked)
    }
}

/**
 * TODO
 * 일정 클릭이벤트 어디에 정의해둘건지 정해야함.
 *  1. baseCalendar abstract로 수정하고 onClicked 함수 선언해두고 외부에서 구현하는방법.
 *  2. 외부에서 파라미터로 전달해주는것.
 *  일단은 2번방법으로
 */
@ExperimentalFoundationApi
@Composable
fun DrawBaseCalendar(viewModel: CalendarViewModel, onCalendarItemClicked: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        DrawCalendarTitleArea(viewModel.calendar)
        DrawCalendar(calendar = viewModel.calendar) {
            Timber.d("it: $it")
            onCalendarItemClicked(it)
        }
    }
}

/**
 * 달력의 타이틀 그리는 함수로 년도와 현재 월, 이전/다음 달로 이동할 수 있는 버튼을 포함
 */
@Composable
fun DrawCalendarTitleArea(baseCalendar: BaseCalendar) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(46.dp)
            .padding(start = 17.dp, end = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = baseCalendar.currentDateTime.value,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_left_arrow),
            contentDescription = "",
            Modifier
                .size(12.dp)
                .clickable {
                    baseCalendar.changeToPrevMonth {
                        Timber.d("prevMonthClicked")
                    }
                }
        )
        Spacer(modifier = Modifier.width(42.dp))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_right_arrow),
            contentDescription = "",
            Modifier
                .size(12.dp)
                .clickable {
                    baseCalendar.changeToNextMonth {
                        Timber.d("nextMonthClicked")
                    }
                }
        )
    }
}

/**
 * 달력 그리기
 */
@ExperimentalFoundationApi
@Composable
fun DrawCalendar(calendar: BaseCalendar, onItemClicked: (String) -> Unit) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(7),
            content = {
                items(calendar.data.size) { index ->
                    DayItem(
                        data = calendar.data[index],
                        onClick = {
                            onItemClicked(calendar.data[index].day.toString())
                            Timber.d("itemClicked :: dat is ${calendar.data[index].day}")
                        },
                        height = maxHeight / calendar.getRowOfCalendar().dp
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}

/**
 * calendar에 그려질 하나의 날짜 셀
 */
@Composable
fun DayItem(data: BaseCalendar.DataModel, height: Float, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .height(height.dp)
            .clickable { onClick() }) {
        Text(
            text = data.day.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .height(24.dp)
                .align(CenterHorizontally)
        )
        Text(
            text = "할일1", modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth()
        )
        Text(
            text = "할일12", modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth()
        )
        Text(
            text = "할일3333333",
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        )
    }
}