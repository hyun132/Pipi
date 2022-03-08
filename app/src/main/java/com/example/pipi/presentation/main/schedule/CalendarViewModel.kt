package com.example.pipi.presentation.main.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.domain.model.schedule.SetInfo
import com.example.pipi.domain.use_case.GetMonthlyScheduleUseCase
import com.example.pipi.global.constants.utils.CalendarUtils.DAYS_OF_WEEK
import com.example.pipi.global.constants.utils.CalendarUtils.ROW_OF_CALENDAR
import com.example.pipi.global.constants.utils.CalendarUtils.changeToNextMonth
import com.example.pipi.global.constants.utils.CalendarUtils.changeToNextYear
import com.example.pipi.global.constants.utils.CalendarUtils.changeToPrevMonth
import com.example.pipi.global.constants.utils.CalendarUtils.changeToPrevYear
import com.example.pipi.global.constants.utils.CalendarUtils.setCalendarDataAndDraw
import com.example.pipi.global.result.Result
import com.example.pipi.presentation.main.schedule.model.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

/**
 * 화면에 뿌려줄 데이터는 여기서 관리한다. 화면 그려주는 역할만 BaseCalendar가 해주는것.
 * TODO 01/23 : 캘린더에 입력할 데이터들은 여기서 관리/변경하고 화면에 그려야 할 때 basecalendar의 calendar에 값을 set해준다.
 * BaseCalendar에 있는 이전/다음달로 넘어가는 부분도 여기서 관리하는게 맞는것 같다.
 * calendarview topappbar, modal창에 들어갈 데이터들 모두 여기서 관리한다.
 */
@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel(val getMonthlyScheduleUseCase: GetMonthlyScheduleUseCase) : ViewModel() {

    val calendar: Calendar = Calendar.getInstance()

//    val state = CalendarViewState() ui관련 state도 클래스로 묶을 것.

    var loadedlastData = mutableMapOf<String, MutableList<ExerciseSchedule>>()  //지난달 데이터
    var loadedCurrentData = mutableMapOf<String, MutableList<ExerciseSchedule>>() // 이번달 데이터
    var loadedNextData = mutableMapOf<String, MutableList<ExerciseSchedule>>()  //다음달 데이터

    var data = MutableList<ScheduleItem>(ROW_OF_CALENDAR* DAYS_OF_WEEK) { ScheduleItem() }

    var year = MutableLiveData(calendar.get(Calendar.YEAR))
    val month = MutableLiveData(calendar.get(Calendar.MONTH))
    val day = MutableLiveData(calendar.get(Calendar.DATE))

    init {
        setDummyData()
    }

    /**
     * 이번달 캘린더 페이지에 보여질 이전달, 이번달,다음달 데이터 불러오는 함수
     * 이번달 1일이 있는 저번달 마지막주, 이번달 마지막날과 같은 주인 다음달 첫주 데이터도 그려주어야 하기 때문
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCurrentMonthPageData() {
        viewModelScope.launch {
            getMonthlyScheduleUseCase(
                GetMonthlyScheduleUseCase.Params(
                    "01092875815",
                    true,
                    "$year-$month"
                )
            ).onEach {
                when (it) {
                    is Result.Success -> {
                        Timber.i("result Success")
                        loadedCurrentData = (it.data?.scheduleDto?.toScheduleItem() ?: mutableMapOf())
                    }
                    is Result.Error -> {
                        Timber.i("result Error")
                    }
                    is Result.Loading -> {
                        Timber.i("result Loading")
                    }
                }
            }.launchIn(this)
        }
    }

    /**
     * modal에서는 월 까지만 선택가능하므로 일은 1일로 초기화(월단위로 화면에 데이터뿌려주기 때문에 영향없음
     */
    fun setCalendar(year: Int, month: Int, date: Int = 1) {
        calendar.set(year, month, date)
        setDay()
    }

    /**
     * 객체 내의 state변경은 관찰되지 않기 때문에 변수로 빼 두고 변수에 갱신된 값 반영될 수 있도록 하는 함수.
     */
    private fun setDay() {
        month.value = calendar.get(Calendar.MONTH)
        year.value = calendar.get(Calendar.YEAR)
    }

    /**
     * calendar의 현재 날짜를 기반으로 스케쥴을 그려준다.
     */
    fun setDayAndDrawTrainingSchedule() {
        calendar.setCalendarDataAndDraw(
            year.value!!,
            month.value!!,
            loadedlastData,
            loadedCurrentData,
            loadedNextData,
            data
        ) {}
    }

    /**
     * calendar의 month를 다음달로 변경
     */
    fun moveNextMonth() {
        calendar.changeToNextMonth { setDay() }

    }

    /**
     * calendar의 month를 이전달로 변경
     */
    fun movePrevMonth() {
        calendar.changeToPrevMonth { setDay() }
    }

    /**
     * calendar의 year를 다음해로 변경
     */
    fun moveNextYear() {
        calendar.changeToNextYear { setDay() }
    }

    /**
     * calendar의 year를 이전해로 변경
     */
    fun movePrevYear() {
        calendar.changeToPrevYear { setDay() }
    }


    fun setDummyData(){
        loadedCurrentData = mutableMapOf("2022-03-01" to mutableListOf<ExerciseSchedule>(ExerciseSchedule("운동1",Parts.ABS,0,ScheduleType.PT,
            listOf(SetInfo("10","30")))),
            "2022-03-31" to mutableListOf<ExerciseSchedule>(ExerciseSchedule("운동31",Parts.BACK,0,ScheduleType.PT,
                listOf(SetInfo("10","30"),SetInfo("20","40"),SetInfo("30","50")))))
        loadedlastData = mutableMapOf("2022-02-01" to mutableListOf<ExerciseSchedule>(ExerciseSchedule("운동1",Parts.ABS,0,ScheduleType.PT,
            listOf(SetInfo("10","30")))),
            "2022-02-28" to mutableListOf<ExerciseSchedule>(ExerciseSchedule("운동28",Parts.AEROBIC,0,ScheduleType.PT,
                listOf(SetInfo("10","30")))))
        loadedNextData = mutableMapOf("2022-04-01" to mutableListOf<ExerciseSchedule>(ExerciseSchedule("운동41",Parts.ABS,0,ScheduleType.PT,
            listOf(SetInfo("10","30")))),
            "2022-04-02" to mutableListOf<ExerciseSchedule>(ExerciseSchedule("운동42",Parts.CHEST,0,ScheduleType.PT,
                listOf(SetInfo("10","30"),SetInfo("10","30"),SetInfo("10","30")))))
    }
}