package com.example.pipi.presentation.main.schedule

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
import androidx.compose.ui.unit.sp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.BRAND_SECOND
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST
import com.example.pipi.global.constants.utils.CalendarUtils.getRowOfCalendar
import com.example.pipi.global.constants.utils.hideModalBottomSheet
import com.example.pipi.global.constants.utils.showModalBottomSheet
import com.example.pipi.presentation.main.schedule.model.ScheduleItem
import com.example.pipi.presentation.main.schedule.model.setColorByParts
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
    state: ModalBottomSheetState,
    onCalendarItemClicked: () -> Unit,
) {
//    val state = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden
//    )
    viewModel.setDayAndDrawTrainingSchedule()
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
        onPrevClick = { viewModel.movePrevYear() },
        onNextClick = { viewModel.moveNextYear() },
    ) { _year, _month ->
        viewModel.setCalendar(_year, _month)
        viewModel.setDayAndDrawTrainingSchedule()
        state.hideModalBottomSheet(scope)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DrawTrainingCalendar(
    viewModel: CalendarViewModel,
    onTitleClick: () -> Unit,
    onCalendarItemClicked: () -> Unit
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
            onCalendarItemClicked()
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
            Spacer(modifier = Modifier.width(8.dp))
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
 * TODO : 달력 다음달/이전달로 옮길ㄸ ㅐ 데이터 변경시켜주는 부분 추가해야함.
 */
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun DrawCalendar(
    calendar: Calendar,
    datas: MutableList<ScheduleItem>,
    onItemClicked: (Calendar) -> Unit,
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(DAYS_OF_WEEK),
            content = {
                items(datas) { item ->
                    DayItem(
                        data = item,
                        color = if (item.date?.monthValue == calendar.get(Calendar.MONTH) + 1) {
                            if (item.date.dayOfMonth == Calendar.getInstance().get(Calendar.DATE)
                            ) BRAND_SECOND else PRIMARY_TEXT
                        } else SECONDARY_TEXT_GHOST,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(maxHeight / getRowOfCalendar())
                            .padding(top = 8.dp)
                            .clickable {
                                val selectedCalendar = Calendar.getInstance()
                                item.date?.let {
                                    selectedCalendar.set(
                                        it.year,
                                        it.monthValue,
                                        it.dayOfMonth
                                    )
                                }
                                onItemClicked(selectedCalendar)
                                Timber.d("itemClicked :: dat is ${item.date?.dayOfMonth}")
                            }
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
 * scheduleData는 해당 날짜에 해당하는만큼만 필터링해서 넘겨받는다.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayItem(
    data: ScheduleItem,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = data.date?.dayOfMonth.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .height(24.dp)
                .align(CenterHorizontally),
            color = color
        )
        // 여기서 데이터는 최대 5개만 출력되어야 한다.
        Column() {
            for (idx in 0 until minOf(5, data.dayExercise.size)) {
                Text(
                    text = "${data.dayExercise[idx].parts} ${data.dayExercise[idx].sets.size}",
                    modifier = Modifier
                        .background(
                            color = setColorByParts(data.dayExercise[idx].parts),
                            shape = RoundedCornerShape(2.8.dp),
                        )
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2
                )
            }
        }

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
                modifier = Modifier.weight(1F),
                fontSize = 15.sp,
                textAlign = TextAlign.Center
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
    }, sheetState = sheetState, sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)) {
    }
}