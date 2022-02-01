package com.example.pipi.presentation.main.calendar

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pipi.global.constants.utils.CalendarUtils
import com.example.pipi.global.constants.utils.CalendarUtils.changeToNextMonth
import com.example.pipi.global.constants.utils.CalendarUtils.changeToNextYear
import com.example.pipi.global.constants.utils.CalendarUtils.changeToPrevMonth
import com.example.pipi.global.constants.utils.CalendarUtils.changeToPrevYear
import com.example.pipi.global.constants.utils.CalendarUtils.setCalendarDataAndDraw
import java.util.*

/**
 * 화면에 뿌려줄 데이터는 여기서 관리한다. 화면 그려주는 역할만 BaseCalendar가 해주는것.
 * TODO 01/23 : 캘린더에 입력할 데이터들은 여기서 관리/변경하고 화면에 그려야 할 때 basecalendar의 calendar에 값을 set해준다.
 * BaseCalendar에 있는 이전/다음달로 넘어가는 부분도 여기서 관리하는게 맞는것 같다.
 * calendarview topappbar, modal창에 들어갈 데이터들 모두 여기서 관리한다.
 */
class CalendarViewModel : ViewModel() {

    val calendar: Calendar = Calendar.getInstance()

    //서버에서 가져올 데이터도 어떻게 넘겨올건지를 고민해볼 필요가 있다.
    var data = mutableStateListOf<CalendarUtils.DataModel>()

    var year = MutableLiveData(calendar.get(Calendar.YEAR))
    val month = MutableLiveData(calendar.get(Calendar.MONTH))
    val day = MutableLiveData(calendar.get(Calendar.DATE))

    init {
        calendar.setCalendarDataAndDraw(
            year.value!!,
            month.value!!,
            data, {})
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
            data, {})
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

}