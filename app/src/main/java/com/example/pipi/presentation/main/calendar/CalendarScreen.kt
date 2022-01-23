package com.example.pipi.presentation.main.calendar

import BaseCalendar.Companion.DAYS_OF_WEEK
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.pipi.global.constants.ui.Colors.BRAND_SECOND
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.utils.CalendarUtils
import com.example.pipi.global.constants.utils.CalendarUtils.changeToNextMonth
import com.example.pipi.global.constants.utils.CalendarUtils.changeToPrevMonth
import com.example.pipi.global.constants.utils.CalendarUtils.getRowOfCalendar
import com.example.pipi.global.constants.utils.hideModalBottomSheet
import com.example.pipi.global.constants.utils.showModalBottomSheet
import okhttp3.internal.trimSubstring
import timber.log.Timber
import java.time.Month
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    onCalendarItemClicked: (String) -> Unit,
) {
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()
    val month = viewModel.month.observeAsState()
    val year = viewModel.year.observeAsState()
    Column(Modifier.fillMaxSize()) {
        DrawTrainingCalendar(
            viewModel = viewModel,
            onTitleClick = { state.showModalBottomSheet(scope);viewModel.setDayAndDrawTrainingSchedule() },
            onCalendarItemClicked = onCalendarItemClicked
        )
    }
    /**
     * year.value!! 이 부분 오류 발생할수 있으므로 변경해야함.
     */
    DrawModalBottomSheet(
        sheetState = state,
        currentYear = year.value!!,
        currentMonth = month.value!!,
        onPrevClick = { viewModel.calendar.changeToPrevMonth { } },
        onNextClick = { viewModel.calendar.changeToNextMonth { } },
    ) { _year, _month ->
        viewModel.setCalendar(_year, _month)
        state.hideModalBottomSheet(scope)
    }
}

/**
 * TODO
 * 일정 클릭이벤트 어디에 정의해둘건지 정해야함.
 *  1. baseCalendar abstract로 수정하고 onClicked 함수 선언해두고 외부에서 구현하는방법.
 *  2. 외부에서 파라미터로 전달해주는것.
 *  일단은 2번방법으로
 */
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DrawTrainingCalendar(
    viewModel: CalendarViewModel,
    onTitleClick: () -> Unit,
    onCalendarItemClicked: (String) -> Unit
) {
    val datas = viewModel.data
    val year = viewModel.year.observeAsState(initial = Calendar.getInstance().get(Calendar.YEAR))
    val month = viewModel.month.observeAsState(initial = Calendar.getInstance().get(Calendar.MONTH))
    Column(modifier = Modifier.fillMaxSize()) {
        viewModel.day.value?.let {
            DrawCalendarTitleArea(
                year,
                month,
                it,
                onYearClick = { onTitleClick() },
                onLeftIconClick = { viewModel.movePrevMonth();viewModel.setDayAndDrawTrainingSchedule() },
                onRightIconClick = { viewModel.moveNextMonth();viewModel.setDayAndDrawTrainingSchedule() }
            )
        }
        DrawCalendar(calendar = viewModel.calendar, datas = datas) {
            onCalendarItemClicked(it)
        }
    }
}


/**
 * 달력의 타이틀 그리는 함수로 년도와 현재 월, 이전/다음 달로 이동할 수 있는 버튼을 포함
 */
@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun DrawCalendarTitleArea(
    year: State<Int>,
    month: State<Int>,
    day: Int,
    onYearClick: () -> Unit,
    onRightIconClick: () -> Unit,
    onLeftIconClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(46.dp)
            .padding(start = 17.dp, end = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
            onYearClick()
        }) {
            Text(
                text = "${year.value}년 ${month.value + 1}월"
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_drop_down),
                contentDescription = "날짜 선택"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_left_arrow),
            contentDescription = "",
            Modifier
                .size(12.dp)
                .clickable {
                    onLeftIconClick()
                }
        )
        Spacer(modifier = Modifier.width(42.dp))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_right_arrow),
            contentDescription = "",
            Modifier
                .size(12.dp)
                .clickable {
                    onRightIconClick()
                }
        )
    }
}

/**
 * 일정 데이터 받아와서 화면에 그려주는 함수
 */
@ExperimentalFoundationApi
@Composable
fun DrawCalendar(
    calendar: Calendar,
    datas: MutableList<CalendarUtils.DataModel>,
    onItemClicked: (String) -> Unit,
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(DAYS_OF_WEEK),
            content = {
                items(datas) { item ->
                    DayItem(
                        data = item,
                        onClick = {
                            onItemClicked(item.day.toString())
                            Timber.d("itemClicked :: dat is ${item.day}")
                        },
                        height = maxHeight / getRowOfCalendar().dp
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
fun DayItem(data: CalendarUtils.DataModel, height: Float, onClick: () -> Unit) {
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
                .background(Color.Blue)
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
        )
    }
}

@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun DrawModalBottomSheet(
    sheetState: ModalBottomSheetState,
    currentYear: Int,
    currentMonth: Int,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onItemClick: (year: Int, month: Int) -> Unit,
) {

    ModalBottomSheetLayout(sheetContent = {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 24.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_left_arrow),
                contentDescription = "",
                Modifier
                    .size(12.dp)
                    .clickable {
                        onPrevClick()
                    }
            )
            Text(
                text = currentYear.toString(),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.weight(1F)
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                Modifier
                    .size(12.dp)
                    .clickable {
                        onNextClick()
                    }
            )
        }

        LazyVerticalGrid(cells = GridCells.Fixed(3), content = {
            items(12) { month ->
                Text(
                    text = Month.values()[month].name.trimSubstring(0, 3),
                    modifier = Modifier
                        .width(84.dp)
                        .height(52.dp)
                        .background(
                            color = if (currentMonth == month) BRAND_SECOND else
                                Color.Unspecified,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            onItemClick(currentYear, month)
                        }
                        .wrapContentHeight(),
                    color = if (currentMonth == month) Color.White else PRIMARY_TEXT,
                    textAlign = TextAlign.Center,
                )
            }
        }, modifier = Modifier.padding(start = 61.dp, end = 61.dp))
    }, sheetState = sheetState) {
    }
}