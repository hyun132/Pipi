package com.example.pipi.global.constants.utils

import timber.log.Timber
import java.io.Serializable
import java.util.*
import kotlin.math.ceil


/**
 * Calendar를 이용하여 화면 그릴 때 필요한 요소들
 */
object CalendarUtils : Serializable {

    const val DAYS_OF_WEEK = 7

    private var ROW_OF_CALENDAR = 6 // 이 값을 동적으로 변환하는 것에 대한 고려 필요

    // 이번달 첫주와 같은 주에 있는 지난달의 일 중 첫번째날 (지난달 마지막주 일요일)
    private var prevMonthTail = 0

    // 이번달 마지막주와 같은 주에 있는 다음달의 일 중 마지막날(다음달 마지막주 토요일)
    private var nextMonthHead = 0

    // 이번달 날짜갯수(마지막 날짜 28,29,30,31)
    private var currentMonthMaxDate = 0

    data class DataModel(
        val year: Int = Calendar.getInstance().get(Calendar.YEAR),
        val day: Int = 1,
        val month: Int = 1,
        val schedules: MutableList<String> = mutableListOf<String>()
    ) : Serializable // 추후 아이템 관련 api 받으면 데이터타입 수정 및 파일분리 예정.

    fun getRowOfCalendar() = ROW_OF_CALENDAR

    fun Calendar.getDate() = this.get(Calendar.DATE)
    fun Calendar.getYear() = this.get(Calendar.YEAR)
    fun Calendar.getMonth() = this.get(Calendar.MONTH)

    fun Calendar.getCurrentDateString():String{
        return "${this.getYear()}년 ${this.getMonth()+1}월 ${this.getDate()}일"
    }

    /**
     * 화면에 표시될 월을 기반으로 화면에 트레이닝 스케쥴 그려준다.
     * @param data
     */
    fun Calendar.setCalendarDataAndDraw(
        year: Int,
        month: Int,
        data: MutableList<DataModel>,
        onDataChanged: (MutableList<DataModel>) -> Unit
    ) {
        this.set(year, month, 1)
        this.drawMonthDate(data) { Timber.d("change calendar date");onDataChanged(data) }
    }

    fun Calendar.changeToPrevYear(dataChangedCallback: (Calendar) -> Unit) {
        this.set(Calendar.YEAR, this.getYear() - 1)
        dataChangedCallback(this)
    }

    fun Calendar.changeToNextYear(dataChangedCallback: (Calendar) -> Unit) {
        this.set(Calendar.YEAR, this.getYear() + 1)
        dataChangedCallback(this)
    }

    fun Calendar.changeToPrevMonth(dataChangedCallback: (Calendar) -> Unit) {
        if (this.getMonth() == Calendar.JANUARY) {
            this.set(Calendar.YEAR, this.getYear() - 1)
            this.set(Calendar.MONTH, Calendar.DECEMBER)
        } else {
            this.set(Calendar.MONTH, this.getMonth() - 1)
        }
        dataChangedCallback(this)
    }

    /**
     * calendar의 월을 다음달로 변경
     */
    fun Calendar.changeToNextMonth(dataChangedCallback: (Calendar) -> Unit) {
        if (this.getMonth() == Calendar.DECEMBER) {
            this.set(Calendar.YEAR, this.getYear() + 1)
            this.set(Calendar.MONTH, Calendar.JANUARY)
        } else {
            this.set(Calendar.MONTH, this.getMonth() + 1)
        }
        dataChangedCallback(this)
    }

    /**
     * 이번달 달력에 보일 다음달 날짜/데이터
     */
    private fun makeNextMonthHeadToShow(data: MutableList<DataModel>, calendar: Calendar) {
        for (i in 1..nextMonthHead) {
            data.add(DataModel(day = i, month = calendar.getMonth()))
        }
    }

    /**
     * 이번달 달력에 보일 이전달 마지막주 날짜들
     */
    private fun makePrevMonthTailToShow(calendar: Calendar, data: MutableList<DataModel>) {
        calendar.set(Calendar.MONTH, calendar.getMonth() - 1) // 지난달 가져옴
        val maxDate = calendar.getActualMaximum(Calendar.DATE) // 가져온 달의 마지막 날짜
        val maxOffsetDate = maxDate - prevMonthTail // 이번달 달력에 보여진 지난달 날짜 시작일

        for (i in maxOffsetDate + 1..maxDate) data.add(
            DataModel(
                day = i,
                month = calendar.getMonth()
            )
        )
    }

    /**
     * 이번 달의 데이터를 가져오는 함수
     */
    private fun makeCurrentMonth(calendar: Calendar, data: MutableList<DataModel>) {
        for (i in 1..calendar.getActualMaximum(Calendar.DATE)) data.add(
            DataModel(
                day = i,
                month = calendar.getMonth()
            )
        )
    }

    /**
     *  입력된 달의 달력 그려주는 함수
     */
    private fun Calendar.drawMonthDate(
        data: MutableList<DataModel>,
        dataChangedCallback: (List<DataModel>) -> Unit
    ) {
//        // 기존 데이터 먼저 지우기
        data.clear()

        currentMonthMaxDate = this.getActualMaximum(Calendar.DAY_OF_MONTH) //이번달 마지막날짜

        prevMonthTail = this.get(Calendar.DAY_OF_WEEK) - 1 //날짜가 1일로 설정되어있기 때문에 이번달 첫주의 1일이전의날

        ROW_OF_CALENDAR = ceil((prevMonthTail + currentMonthMaxDate) / 7.0).toInt()

        //이전달 마지막주 데이터를 넘겨주어야함. 일단
        makePrevMonthTailToShow(
            this.clone() as Calendar,
            data
        ) //깊은복사 (Calendar class 내부에서 재정의해주었기때문)
        makeCurrentMonth(this, data)

        nextMonthHead = ROW_OF_CALENDAR * DAYS_OF_WEEK - (prevMonthTail + currentMonthMaxDate)
        makeNextMonthHeadToShow(calendar = this.clone() as Calendar, data = data)

        dataChangedCallback(data)
    }
}