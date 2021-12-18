package com.example.pipi.global.constants.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.io.Serializable
import java.util.*
import kotlin.math.ceil

class BaseCalendar : Serializable {

    companion object {
        const val DAYS_OF_WEEK = 7
    }

    var ROW_OF_CALENDAR = 6 // 이 값을 동적으로 변환하는 것에 대한 고려 필요

    val calendar = Calendar.getInstance()

    // 이번달 첫주와 같은 주에 있는 지난달의 일 중 첫번째날 (지난달 마지막주 일요일)
    var prevMonthTail = 0

    // 이번달 마지막주와 같은 주에 있는 다음달의 일 중 마지막날(다음달 마지막주 토요일)
    var nextMonthHead = 0

    // 이번달 날짜갯수(마지막 날짜 28,29,30,31)
    var currentMonthMaxDate = 0

    // 나중에 Int -> '데이터 모델 타입' 으로 변경할 것
    // 서버에서 데이터 어떻게 보내줄건지.(날짜별? 월별? 일별로 요청해야하나?)
    var data = mutableStateListOf<DataModel>()

    /**
     * 현재 년/월 을 텍스트로 가지고 있는 변수
     */
    val currentDateTime: MutableState<String> =
        mutableStateOf("${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH)}월")

    data class DataModel(
        val day: Int = 1,
        val schedules: MutableList<String> = mutableListOf<String>()
    ):Serializable // 추후 아이템 관련 api 받으면 데이터타입 수정 및 파일분리 예정.

    init {
        calendar.time = Date() // 오늘로 설정?
    }

    fun getRowOfCalendar() = ROW_OF_CALENDAR

    fun initBaseCalendar(dataChangedCallback: (Calendar) -> Unit) {
        drawMonthDate(dataChangedCallback)
    }

    fun changeToPrevMonth(dataChangedCallback: (Calendar) -> Unit) {
        if (calendar.get(Calendar.MONTH) == Calendar.JANUARY) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1)
            calendar.set(Calendar.MONTH, Calendar.DECEMBER)
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        }
        drawMonthDate(dataChangedCallback)
//        dataChangedCallback(calendar)
    }

    fun changeToNextMonth(dataChangedCallback: (Calendar) -> Unit) {
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
            calendar.set(Calendar.MONTH, Calendar.JANUARY)
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
        }
        drawMonthDate(dataChangedCallback)
//        dataChangedCallback(Calendar)
    }

    /**
     * 이번달 달력에 보일 다음달날짜
     */
    private fun makeNextMonthHeadToShow() {
        for (i in 1..nextMonthHead) {
            data.add(DataModel(i))
        }
    }

    /**
     * 이번달 달력에 보일 이전달 마지막주 날짜들
     */
    private fun makePrevMonthTailToShow(calendar: Calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1) // 지난달 가져옴

        val maxDate = calendar.getActualMaximum(Calendar.DATE) // 가져온 달의 마지막 날짜
        val maxOffsetDate = maxDate - prevMonthTail // 이번달 달력에 보여진 지난달 날짜 시작일

        for (i in maxOffsetDate + 1..maxDate) data.add(DataModel(i))
//        for (i in 1..prevMonthTailOffset) data.add(++maxOffsetDate)
    }

    private fun makeCurrentMonth(calendar: Calendar) {
        for (i in 1..calendar.getActualMaximum(Calendar.DATE)) data.add(DataModel(i))
    }

    /**
     *  입력된 달의 달력 그려주는 함수
     */
    private fun drawMonthDate(dataChangedCallback: (Calendar) -> Unit) {
        // 기존 데이터 먼저 지우기
        data.clear()

        calendar.set(Calendar.DATE, 1) // 기본 날짜를 1로 설정.

        currentMonthMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) //이번달 마지막날짜

        prevMonthTail = calendar.get(Calendar.DAY_OF_WEEK) - 1 //날짜가 1일로 설정되어있기 때문에 이번달 첫주의 1일이전의날

        ROW_OF_CALENDAR = ceil((prevMonthTail + currentMonthMaxDate) / 7.0).toInt()

        makePrevMonthTailToShow(calendar.clone() as Calendar) //깊은복사 (Calendar class 내부에서 재정의해주었기때문)
        makeCurrentMonth(calendar)
        currentDateTime.value =
            "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}"

        nextMonthHead = ROW_OF_CALENDAR * DAYS_OF_WEEK - (prevMonthTail + currentMonthMaxDate)
        makeNextMonthHeadToShow()

        dataChangedCallback(calendar)
    }
}